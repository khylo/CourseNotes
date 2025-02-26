package com.example.devextremespringboot.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;

import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.devextremespringboot.odata.EmployeeEdmProvider;
import com.example.devextremespringboot.odata.EmployeeEntityCollectionProcessor;
import com.example.devextremespringboot.repository.EmployeeRepository;

@Configuration
public class OlingoConfig {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Bean
    public ServletContextInitializer oDataServletInitializer() {
        return servletContext -> {
            ServletRegistration.Dynamic oDataServlet = servletContext.addServlet("ODataServlet", 
                (req, resp) -> {
                    try {
                        // Create Olingo OData handler and configure it
                        OData odata = OData.newInstance();
                        ServiceMetadata edm = odata.createServiceMetadata(
                            new EmployeeEdmProvider(), new ArrayList<>());
                        ODataHttpHandler handler = odata.createHandler(edm);

                        // Register processors
                        handler.register(new EmployeeEntityCollectionProcessor(employeeRepository));

                        // Process request
                        handler.process(req, resp);
                    } catch (RuntimeException | ODataException e) {
                        throw new ServletException(e);
                    }
                }
            );
            
            oDataServlet.addMapping("/odata/*");
            oDataServlet.setLoadOnStartup(1);
        };
    }
}
