package com.squirrel;

import com.squirrel.config.DatabaseSetup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class WebAppApplication {

	public static void main(String[] args) {
		DatabaseSetup databaseSetup=new DatabaseSetup();
		databaseSetup.setUpDatabase();
		SpringApplication.run(WebAppApplication.class, args);
	}

}