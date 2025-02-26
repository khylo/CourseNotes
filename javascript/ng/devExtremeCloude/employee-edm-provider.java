package com.example.devextremespringboot.odata;

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
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;

public class EmployeeEdmProvider extends CsdlAbstractEdmProvider {

    // Service Namespace
    public static final String NAMESPACE = "com.example.devextremespringboot";

    // EDM Container
    public static final String CONTAINER_NAME = "Container";
    public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

    // Entity Types
    public static final String ET_EMPLOYEE_NAME = "Employee";
    public static final FullQualifiedName ET_EMPLOYEE_FQN = new FullQualifiedName(NAMESPACE, ET_EMPLOYEE_NAME);

    // Entity Sets
    public static final String ES_EMPLOYEES_NAME = "Employees";

    @Override
    public List<CsdlSchema> getSchemas() throws ODataException {
        CsdlSchema schema = new CsdlSchema();
        schema.setNamespace(NAMESPACE);

        // EntityTypes
        List<CsdlEntityType> entityTypes = new ArrayList<>();
        entityTypes.add(getEntityType(ET_EMPLOYEE_FQN));
        schema.setEntityTypes(entityTypes);

        // EntityContainer
        schema.setEntityContainer(getEntityContainer());

        // Return schemas
        return Collections.singletonList(schema);
    }

    @Override
    public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {
        if (entityTypeName.equals(ET_EMPLOYEE_FQN)) {
            // Create a key property
            CsdlPropertyRef propertyRef = new CsdlPropertyRef();
            propertyRef.setName("id");

            // Create properties
            CsdlProperty id = new CsdlProperty()
                    .setName("id")
                    .setType(EdmPrimitiveTypeKind.Int64.getFullQualifiedName())
                    .setNullable(false);

            CsdlProperty firstName = new CsdlProperty()
                    .setName("firstName")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(50);

            CsdlProperty lastName = new CsdlProperty()
                    .setName("lastName")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(50);

            CsdlProperty position = new CsdlProperty()
                    .setName("position")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(100);

            CsdlProperty department = new CsdlProperty()
                    .setName("department")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(100);

            CsdlProperty salary = new CsdlProperty()
                    .setName("salary")
                    .setType(EdmPrimitiveTypeKind.Decimal.getFullQualifiedName());

            CsdlProperty hireDate = new CsdlProperty()
                    .setName("hireDate")
                    .setType(EdmPrimitiveTypeKind.Date.getFullQualifiedName());

            CsdlProperty email = new CsdlProperty()
                    .setName("email")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(100);

            CsdlProperty phone = new CsdlProperty()
                    .setName("phone")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(20);

            CsdlProperty address = new CsdlProperty()
                    .setName("address")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(200);

            CsdlProperty city = new CsdlProperty()
                    .setName("city")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(100);

            CsdlProperty state = new CsdlProperty()
                    .setName("state")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(50);

            CsdlProperty zipCode = new CsdlProperty()
                    .setName("zipCode")
                    .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
                    .setMaxLength(10);

            // Create the entity type
            CsdlEntityType entityType = new CsdlEntityType();
            entityType.setName(ET_EMPLOYEE_NAME);
            entityType.setProperties(Arrays.asList(id, firstName, lastName, position, department, 
                                      salary, hireDate, email, phone, address, city, state, zipCode));
            entityType.setKey(Collections.singletonList(propertyRef));

            return entityType;
        }

        return null;
    }

    @Override
    public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) throws ODataException {
        if (entityContainer.equals(CONTAINER)) {
            if (entitySetName.equals(ES_EMPLOYEES_NAME)) {
                CsdlEntitySet entitySet = new CsdlEntitySet();
                entitySet.setName(ES_EMPLOYEES_NAME);
                entitySet.setType(ET_EMPLOYEE_FQN);
                return entitySet;
            }
        }
        return null;
    }

    @Override
    public CsdlEntityContainer getEntityContainer() throws ODataException {
        CsdlEntityContainer entityContainer = new CsdlEntityContainer();
        entityContainer.setName(CONTAINER_NAME);
        
        // Add entity sets
        List<CsdlEntitySet> entitySets = new ArrayList<>();
        entitySets.add(getEntitySet(CONTAINER, ES_EMPLOYEES_NAME));
        entityContainer.setEntitySets(entitySets);

        return entityContainer;
    }

    @Override
    public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {
        if (entityContainerName == null || entityContainerName.equals(CONTAINER)) {
            CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
            entityContainerInfo.setContainerName(CONTAINER);
            return entityContainerInfo;
        }
        return null;
    }
}
