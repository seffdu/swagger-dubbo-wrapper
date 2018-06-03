package com.du.dubbo;

import com.deepoove.swagger.dubbo.annotations.EnableDubboSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

//@SpringBootApplication
//@ImportResource("classpath*:META-INF/spring/*.xml")
//@PropertySource("classpath:swagger-dubbo.properties")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}

