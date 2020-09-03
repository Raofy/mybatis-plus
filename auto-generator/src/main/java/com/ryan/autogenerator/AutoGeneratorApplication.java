package com.ryan.autogenerator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ryan.autogenerator.mapper")
public class AutoGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoGeneratorApplication.class, args);
    }

}
