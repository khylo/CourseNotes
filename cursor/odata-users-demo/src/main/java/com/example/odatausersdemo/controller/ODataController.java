package com.example.odatausersdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.odatausersdemo.odata.UserEdmProvider;
import com.example.odatausersdemo.odata.UserODataService;

import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.util.ArrayList;

@RestController
@RequestMapping("/odata")
public class ODataController {
    
    private static final Logger logger = LoggerFactory.getLogger(ODataController.class);

    @Autowired
    private UserEdmProvider edmProvider;
    
    @Autowired
    private UserODataService userODataService;

    // Handle both with and without trailing slashes
    //@RequestMapping(value = {"/**", "/**//"}, produces = { "application/json", "application/xml" })
    public void process(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Log the incoming request
            String requestURI = request.getRequestURI();
            String queryString = request.getQueryString();
            
            logger.info("Received OData request: {} {}", request.getMethod(), requestURI);
            logger.info("Query string: {}", queryString);
            
            // Create final request variable for use in OData processing
            final HttpServletRequest finalRequest;
            
            // Normalize the URI if it has trailing slashes
            if (requestURI != null && (requestURI.endsWith("//") || requestURI.endsWith("/"))) {
                logger.warn("Request has trailing slash(es), normalizing: {}", requestURI);
                // Create a request wrapper that modifies the URI
                final String normalizedURI = requestURI.replaceAll("/+$", "");
                logger.info("Normalized URI: {}", normalizedURI);
                
                // Use the normalized URI for OData processing
                finalRequest = new HttpServletRequestWrapper(request) {
                    @Override
                    public String getRequestURI() {
                        return normalizedURI;
                    }
                    
                    @Override
                    public StringBuffer getRequestURL() {
                        StringBuffer url = new StringBuffer();
                        url.append(request.getScheme())
                           .append("://")
                           .append(request.getServerName())
                           .append(":")
                           .append(request.getServerPort())
                           .append(normalizedURI);
                        return url;
                    }
                    
                    @Override
                    public String getPathInfo() {
                        String pathInfo = super.getPathInfo();
                        if (pathInfo != null && pathInfo.endsWith("//")) {
                            return pathInfo.replaceAll("/+$", "");
                        }
                        return pathInfo;
                    }
                };
            } else {
                finalRequest = request;
            }
            
            // Create odata handler and configure it with EdmProvider and Processor
            OData odata = OData.newInstance();
            ServiceMetadata edm = odata.createServiceMetadata(edmProvider, new ArrayList<>());
            ODataHttpHandler handler = odata.createHandler(edm);

            // Register the processor
            handler.register(userODataService);

            // Set preferred format to JSON if not specified
            if (finalRequest.getHeader("Accept") == null || finalRequest.getHeader("Accept").isEmpty()) {
                response.setHeader("Content-Type", ContentType.JSON.toContentTypeString());
            }

            // Let the handler do the work
            handler.process(new HttpServletRequestWrapper(finalRequest), new HttpServletResponseWrapper(response));
            
            logger.info("OData request processed successfully");
        } catch (Exception e) {
            logger.error("Error processing OData request", e);
            throw new RuntimeException("Error processing OData request", e);
        }
    }
} 