package com.example.devextremespringboot.config;

import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Component
public class EmployeeODataServiceFactory extends ODataJPAServiceFactory {

    private static final String PERSISTENCE_UNIT_NAME = "default";
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPAException {
        ODataJPAContext oDataJPAContext = getODataJPAContext();
        oDataJPAContext.setEntityManagerFactory(entityManagerFactory);
        oDataJPAContext.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        return oDataJPAContext;
    }
}
