package com.example.odatausersdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
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

    @Autowired
    private UserEdmProvider edmProvider;
    
    @Autowired
    private UserODataService userODataService;

    @RequestMapping("/**")
    public void process(HttpServletRequest request, HttpServletResponse response) {
        OData odata = OData.newInstance();
        ServiceMetadata edm = odata.createServiceMetadata(edmProvider, new ArrayList<>());
        ODataHttpHandler handler = odata.createHandler(edm);
        handler.register(userODataService);
        handler.process(new HttpServletRequestWrapper(request), new HttpServletResponseWrapper(response));
    }
} 