package com.du.dubbo.controller;

import com.du.dubbo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private HelloService helloService;

    @GetMapping("test")
    public String test(){
        System.out.println(helloService.sayHello("asdfffff"));
        return "{\"asdf\":\"asdf\"}";
    }
}
