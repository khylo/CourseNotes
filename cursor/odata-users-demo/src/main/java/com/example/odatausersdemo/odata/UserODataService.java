package com.example.odatausersdemo.odata;

import java.util.List;
import java.net.URI;
import java.util.Locale;
import java.nio.charset.StandardCharsets;

import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.queryoption.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.odatausersdemo.model.User;
import com.example.odatausersdemo.repository.UserRepository;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class UserODataService implements EntityCollectionProcessor {

    private static final Logger logger = LoggerFactory.getLogger(UserODataService.class);
    
    private OData odata;
    private ServiceMetadata serviceMetadata;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void init(OData odata, ServiceMetadata serviceMetadata) {
        this.odata = odata;
        this.serviceMetadata = serviceMetadata;
    }

    @Override
    public void readEntityCollection(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType responseFormat)
            throws ODataApplicationException, SerializerException {
        
        // Log request info for debugging
        logger.info("Processing OData request: {}", request.getRawRequestUri());
        logger.info("Query options: {}", request.getRawQueryPath());
        
        // Get the entity set from the URI
        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);
        EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();
        EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        // Fetch the data from the database
        EntityCollection entityCollection = new EntityCollection();
        List<User> users = userRepository.findAll();
        logger.info("Found {} users in the database", users.size());
        
        // Convert users to entities
        List<Entity> entities = users.stream()
            .map(user -> {
                Entity entity = new Entity();
                entity.addProperty(new Property(null, "id", ValueType.PRIMITIVE, user.getId()));
                entity.addProperty(new Property(null, "firstName", ValueType.PRIMITIVE, user.getFirstName()));
                entity.addProperty(new Property(null, "lastName", ValueType.PRIMITIVE, user.getLastName()));
                entity.addProperty(new Property(null, "title", ValueType.PRIMITIVE, user.getTitle()));
                entity.addProperty(new Property(null, "role", ValueType.PRIMITIVE, user.getRole()));
                entity.addProperty(new Property(null, "email", ValueType.PRIMITIVE, user.getEmail()));
                entity.addProperty(new Property(null, "location", ValueType.PRIMITIVE, user.getLocation()));
                entity.setId(createId(edmEntitySet.getName(), user.getId()));
                return entity;
            })
            .collect(Collectors.toList());

        // Handle the $filter query option manually if present
        String rawQueryPath = request.getRawQueryPath();
        if (rawQueryPath != null && rawQueryPath.contains("$filter")) {
            logger.info("Manually handling $filter: {}", rawQueryPath);
            
            // Basic manual parsing of filter
            if (rawQueryPath.contains("location%20eq%20")) {
                // URL-encoded version of "location eq 'value'"
                String[] parts = rawQueryPath.split("location%20eq%20");
                if (parts.length > 1) {
                    String encodedValue = parts[1];
                    // Handle quoted values
                    if (encodedValue.startsWith("'")) {
                        encodedValue = encodedValue.substring(1);
                    }
                    if (encodedValue.contains("'")) {
                        encodedValue = encodedValue.substring(0, encodedValue.indexOf("'"));
                    }
                    
                    // Handle ampersand for additional query params
                    if (encodedValue.contains("&")) {
                        encodedValue = encodedValue.substring(0, encodedValue.indexOf("&"));
                    }
                    
                    final String location = java.net.URLDecoder.decode(encodedValue, StandardCharsets.UTF_8);
                    logger.info("Filtering by location: {}", location);
                    
                    entities = entities.stream()
                        .filter(e -> {
                            Property locationProp = e.getProperty("location");
                            return locationProp != null && 
                                  location.equals(locationProp.getValue().toString());
                        })
                        .collect(Collectors.toList());
                    
                    logger.info("After filtering, found {} users", entities.size());
                }
            }
        }

        // Apply $skip if present
        SkipOption skipOption = uriInfo.getSkipOption();
        if (skipOption != null) {
            int skip = skipOption.getValue();
            logger.info("Applying $skip: {}", skip);
            if (skip < entities.size()) {
                entities = entities.subList(skip, entities.size());
            } else {
                entities = new ArrayList<>();
            }
        }

        // Apply $top if present
        TopOption topOption = uriInfo.getTopOption();
        if (topOption != null) {
            int top = topOption.getValue();
            logger.info("Applying $top: {}", top);
            if (top < entities.size()) {
                entities = entities.subList(0, top);
            }
        }

        // Apply $select if present
        SelectOption selectOption = uriInfo.getSelectOption();
        if (selectOption != null) {
            logger.info("Applying $select");
            try {
                List<String> selectedProperties = selectOption.getSelectItems().stream()
                    .map(item -> item.getResourcePath().getUriResourceParts().get(0).getSegmentValue())
                    .collect(Collectors.toList());
                
                logger.info("Selected properties: {}", selectedProperties);
                
                entities.forEach(entity -> {
                    List<Property> properties = new ArrayList<>(entity.getProperties());
                    properties.removeIf(property -> !selectedProperties.contains(property.getName()));
                    entity.getProperties().clear();
                    entity.getProperties().addAll(properties);
                });
            } catch (Exception e) {
                logger.error("Error applying $select", e);
            }
        }

        entityCollection.getEntities().addAll(entities);
        logger.info("Returning {} entities", entities.size());

        // Serialize the response
        ODataSerializer serializer = odata.createSerializer(responseFormat);
        ContextURL contextUrl = ContextURL.with().entitySet(edmEntitySet).build();
        
        EntityCollectionSerializerOptions.Builder optionsBuilder = 
            EntityCollectionSerializerOptions.with().contextURL(contextUrl);
        
        if (selectOption != null) {
            optionsBuilder.select(selectOption);
        }
            
        EntityCollectionSerializerOptions opts = optionsBuilder.build();

        response.setContent(serializer.entityCollection(serviceMetadata, edmEntityType, entityCollection, opts).getContent());
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());
    }

    private URI createId(String entitySetName, Long id) {
        try {
            return new URI(String.format("%s(%d)", entitySetName, id));
        } catch (Exception e) {
            return null;
        }
    }
}