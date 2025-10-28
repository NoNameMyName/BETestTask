package com.winwintravel.authapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BETestTaskAuthAPIApplication {

    public static void main(String[] args) {
//        System.out.println(environment.getProperty("JWT.SECRET"));
        SpringApplication.run(BETestTaskAuthAPIApplication.class, args);
    }
}
