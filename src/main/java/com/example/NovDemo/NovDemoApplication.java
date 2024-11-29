package com.example.NovDemo;

import com.example.NovDemo.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NovDemoApplication {

    public static void main(String[] args) {
        Product p = new Product();

        SpringApplication.run(NovDemoApplication.class, args);
    }

}
