package com.example.devextremespringboot.config;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.provider.*;
import org.apache.olingo.odata2.api.exception.ODataException;

import java.util.ArrayList;
import java.util.List;

public class EmployeeEdmProvider extends EdmProvider {
    
    private static final String NAMESPACE = "com.example.devextremespringboot";
    private static final String ENTITY_SET_NAME = "Employees";
    private static final String ENTITY_TYPE_NAME = "Employee";
    private static final String ENTITY_CONTAINER_NAME = "EmployeeContainer";

    @Override
    public List<Schema> getSchemas() throws ODataException {
        List<Schema> schemas = new ArrayList<>();
        
        Schema schema = new Schema();
        schema.setNamespace(NAMESPACE);
        
        List<EntityType> entityTypes = new ArrayList<>();
        entityTypes.add(getEntityType(ENTITY_TYPE_NAME));
        schema.setEntityTypes(entityTypes);
        
        List<EntityContainer> entityContainers = new ArrayList<>();
        entityContainers.add(getEntityContainer());
        schema.setEntityContainers(entityContainers);
        
        schemas.add(schema);
        
        return schemas;
    }

    @Override
    public EntityType getEntityType(String entityTypeName) throws ODataException {
        if (ENTITY_TYPE_NAME.equals(entityTypeName)) {
            List<Property> properties = new ArrayList<>();
            
            properties.add(new SimpleProperty()
                    .setName("id")
                    .setType(EdmSimpleTypeKind.Int64)
                    .setFacets(new Facets().setNullable(false)));
            
            properties.add(new SimpleProperty()
                    .setName("firstName")
                    .setType(EdmSimpleTypeKind.String)
                    .setFacets(new Facets().setNullable(true).setMaxLength(50)));
            
            properties.add(new SimpleProperty()
                    .setName("lastName")
                    .setType(EdmSimpleTypeKind.String)
                    .setFacets(new Facets().setNullable(true).setMaxLength(50)));
            
            properties.add(new SimpleProperty()
                    .setName("position")
                    .setType(EdmSimpleTypeKind.String)
                    .setFacets(new Facets().setNullable(true).setMaxLength(100)));
            
            properties.add(new SimpleProperty()
                    .setName("department")
                    .setType(EdmSimpleTypeKind.String)
                    .setFacets(new Facets().setNullable(true).setMaxLength(100)));
            
            properties.add(new SimpleProperty()
                    .setName("salary")
                    .setType(EdmSimpleTypeKind.Decimal)
                    .setFacets(new Facets().setNullable(true)));
            
            properties.add(new SimpleProperty()
                    .setName("hireDate")
                    .setType(EdmSimpleTypeKind.DateTime)
                    .setFacets(new Facets().setNullable(true)));
            
            properties.add(new SimpleProperty()
                    .setName("email")
                    .setType(EdmSimpleTypeKind.String)
                    .setFacets(new Facets().setNullable(true).setMaxLength(100)));
            
            properties.add(new SimpleProperty()
                    .setName("phone")
                    .setType(EdmSimpleTypeKind.String)
                    .setFacets(new Facets().setNullable(true).setMaxLength(20)));
            
            properties.add(new SimpleProperty()
                    .setName("address")
                    .setType(EdmSimpleTypeKind.String)
                    .setFacets(new Facets().setNullable(true).setMaxLength(200)));
            
            properties.add(new SimpleProperty()
                    .setName("city")
                    .setType(EdmSimpleTypeKind.String)
                    .setFacets(new Facets().setNullable(true).setMaxLength(100)));
            
            properties.add(new SimpleProperty()
                    .setName("state")
                    .setType(EdmSimpleTypeKind.String)
                    .setFacets(new Facets().setNullable(true).setMaxLength(50)));
            
            properties.add(new SimpleProperty()
                    .setName("zipCode")
                    .setType(EdmSimpleTypeKind.String)
                    .setFacets(new Facets().setNullable(true).setMaxLength(10)));
            
            // Create and return the entity type
            Key key = new Key().setKeys(List.of(new PropertyRef().setName("id")));
            return new EntityType().setName(ENTITY_TYPE_NAME)
                                  .setProperties(properties)
                                  .setKey(key);
        }
        
        return null;
    }

    @Override
    public EntitySet getEntitySet(String entityContainer, String entitySetName) throws ODataException {
        if (ENTITY_CONTAINER_NAME.equals(entityContainer) && ENTITY_SET_NAME.equals(entitySetName)) {
            return new EntitySet().setName(ENTITY_SET_NAME)
                                 .setEntityType(new FullQualifiedName(NAMESPACE, ENTITY_TYPE_NAME));
        }
        return null;
    }

    @Override
    public EntityContainer getEntityContainer() throws ODataException {
        List<EntitySet> entitySets = new ArrayList<>();
        entitySets.add(getEntitySet(ENTITY_CONTAINER_NAME, ENTITY_SET_NAME));
        
        return new EntityContainer().setName(ENTITY_CONTAINER_NAME)
                                   .setEntitySets(entitySets)
                                   .setDefaultEntityContainer(true);
    }

    @Override
    public Association getAssociation(FullQualifiedName associationName) throws ODataException {
        return null;
    }

    @Override
    public AssociationSet getAssociationSet(String entityContainer, FullQualifiedName association, 
                                          String sourceEntitySetName, String sourceEntitySetRole) 
                                          throws ODataException {
        return null;
    }

    @Override
    public EntityContainerInfo getEntityContainerInfo(String entityContainerName) throws ODataException {
        if (entityContainerName == null || ENTITY_CONTAINER_NAME.equals(entityContainerName)) {
            return new EntityContainerInfo().setName(ENTITY_CONTAINER_NAME)
                                           .setDefaultEntityContainer(true);
        }
        return null;
    }

    @Override
    public FunctionImport getFunctionImport(String entityContainer, String functionImportName) throws ODataException {
        return null;
    }
}
