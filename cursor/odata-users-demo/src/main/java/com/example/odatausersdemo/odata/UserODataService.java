package com.example.odatausersdemo.odata;

import java.util.List;
import java.util.Locale;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.odatausersdemo.model.User;
import com.example.odatausersdemo.repository.UserRepository;

@Component
public class UserODataService implements EntityCollectionProcessor {

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
        
        // Get the entity set from the URI
        List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
        UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);
        EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();
        EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        // Fetch the data from the database
        EntityCollection entityCollection = new EntityCollection();
        List<User> users = userRepository.findAll();
        
        for (User user : users) {
            Entity entity = new Entity();
            entity.addProperty(new Property(null, "id", ValueType.PRIMITIVE, user.getId()));
            entity.addProperty(new Property(null, "firstName", ValueType.PRIMITIVE, user.getFirstName()));
            entity.addProperty(new Property(null, "lastName", ValueType.PRIMITIVE, user.getLastName()));
            entity.addProperty(new Property(null, "title", ValueType.PRIMITIVE, user.getTitle()));
            entity.addProperty(new Property(null, "role", ValueType.PRIMITIVE, user.getRole()));
            entity.addProperty(new Property(null, "email", ValueType.PRIMITIVE, user.getEmail()));
            entity.addProperty(new Property(null, "location", ValueType.PRIMITIVE, user.getLocation()));
            entityCollection.getEntities().add(entity);
        }

        // Serialize the response
        ODataSerializer serializer = odata.createSerializer(responseFormat);
        ContextURL contextUrl = ContextURL.with().entitySet(edmEntitySet).build();
        EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions.with()
                .contextURL(contextUrl)
                .build();

        response.setContent(serializer.entityCollection(serviceMetadata, edmEntityType, entityCollection, opts).getContent());
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());
    }
}