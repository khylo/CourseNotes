package com.example.odatausersdemo.odata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.springframework.stereotype.Component;

@Component
public class UserEdmProvider extends CsdlAbstractEdmProvider {

    // Service Namespace
    public static final String NAMESPACE = "OData.Demo";
    
    // EDM Container
    public static final String CONTAINER_NAME = "Container";
    public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);
    
    // Entity Type
    public static final String ET_USER_NAME = "User";
    public static final FullQualifiedName ET_USER_FQN = new FullQualifiedName(NAMESPACE, ET_USER_NAME);
    
    // Entity Set
    public static final String ES_USERS_NAME = "Users";

    @Override
    public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) {
        if (ET_USER_FQN.equals(entityTypeName)) {
            CsdlProperty id = new CsdlProperty()
                    .setName("id")
                    .setType(EdmPrimitiveTypeKind.Int64.getFullQualifiedName());
            CsdlProperty firstName = new CsdlProperty()
                    .setName("firstName")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            CsdlProperty lastName = new CsdlProperty()
                    .setName("lastName")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            CsdlProperty title = new CsdlProperty()
                    .setName("title")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            CsdlProperty role = new CsdlProperty()
                    .setName("role")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            CsdlProperty email = new CsdlProperty()
                    .setName("email")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            CsdlProperty location = new CsdlProperty()
                    .setName("location")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

            CsdlPropertyRef propertyRef = new CsdlPropertyRef();
            propertyRef.setName("id");

            return new CsdlEntityType()
                    .setName(ET_USER_NAME)
                    .setProperties(Arrays.asList(id, firstName, lastName, title, role, email, location))
                    .setKey(Collections.singletonList(propertyRef));
        }
        return null;
    }

    @Override
    public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) {
        if (CONTAINER.equals(entityContainer) && ES_USERS_NAME.equals(entitySetName)) {
            return new CsdlEntitySet()
                    .setName(ES_USERS_NAME)
                    .setType(ET_USER_FQN);
        }
        return null;
    }

    @Override
    public CsdlEntityContainer getEntityContainer() {
        List<CsdlEntitySet> entitySets = new ArrayList<>();
        entitySets.add(getEntitySet(CONTAINER, ES_USERS_NAME));
        
        return new CsdlEntityContainer()
                .setName(CONTAINER_NAME)
                .setEntitySets(entitySets);
    }

    @Override
    public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) {
        if (entityContainerName == null || CONTAINER.equals(entityContainerName)) {
            return new CsdlEntityContainerInfo()
                    .setContainerName(CONTAINER);
        }
        return null;
    }

    @Override
    public List<CsdlSchema> getSchemas() {
        List<CsdlSchema> schemas = new ArrayList<>();
        CsdlSchema schema = new CsdlSchema();
        schema.setNamespace(NAMESPACE);
        
        List<CsdlEntityType> entityTypes = new ArrayList<>();
        entityTypes.add(getEntityType(ET_USER_FQN));
        schema.setEntityTypes(entityTypes);
        
        schema.setEntityContainer(getEntityContainer());
        schemas.add(schema);
        
        return schemas;
    }
} 