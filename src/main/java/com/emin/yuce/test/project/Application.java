package com.emin.yuce.test.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		JavaFileSearch myBean = context.getBean(JavaFileSearch.class);
		myBean.test(); // Call the method of the bean
	}

}
