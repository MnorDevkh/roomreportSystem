package com.example.reportsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class ReportsystemApplication {

	public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ReportsystemApplication.class);
        application.setAllowBeanDefinitionOverriding(true);
        application.run(args);

    }


}
