package com.khylo.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {

	@GetMapping("/{msg}")    
    public String hello(@PathVariable String msg) {
        return "<h1>Hello "+msg+"</h1>";
    }

    @GetMapping("")
    public String hello() {
        return "<h1>Hello</h1>";
    }

    @GetMapping("p")
    public String helloParam(@RequestParam("p") String param) {
        return "<h1>Hello</h1><p>Param ="+param+"</p>";
    }

}
