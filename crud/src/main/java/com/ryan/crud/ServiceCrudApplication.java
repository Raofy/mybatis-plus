package com.ryan.crud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ryan.crud.mapper")
public class ServiceCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCrudApplication.class, args);
    }

}
