package com.example.registration;

import com.example.registration.controller.WebController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = WebController.class)
public class ClassRegistrationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassRegistrationSystemApplication.class, args);
	}

}
