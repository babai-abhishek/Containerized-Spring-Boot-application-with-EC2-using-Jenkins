package com.example.customer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerServiceApplication{

	public static void main(String[] args) {
		System.out.println(System.getProperty("property "+"spring.datasource.username"));
		System.out.println(System.getenv("env "+"spring.datasource.username"));
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

}
