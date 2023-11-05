package com.example.registration;

import com.example.registration.configs.AwsConfig;
import com.example.registration.configs.CorsConfig;
import com.example.registration.controller.WebController;
import com.example.registration.model.Courses;
import com.example.registration.model.Student;
import com.example.registration.services.CourseService;
import com.example.registration.services.StudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = { WebController.class, Student.class, StudentService.class, AwsConfig.class, Courses.class, CourseService.class, CorsConfig.class})
public class ClassRegistrationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassRegistrationSystemApplication.class, args);
	}

}
