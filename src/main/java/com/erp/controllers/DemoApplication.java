package com.erp.controllers;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
<<<<<<< HEAD
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

=======
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> 25284e7dbfced36fb43954ef833dc735a8327187

@Configuration
@EntityScan(basePackages = { "com.erp.classes" })
@EnableJpaRepositories(basePackages = { "com.erp.repo" })
@ComponentScan(basePackages = { "com.erp.classes", "com.erp.repo", "com.erp.controllers", "com.erp.services" })
@SpringBootApplication(scanBasePackages = { "com.erp.classes" })
public class DemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }

}
