package com.luiz.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.luiz.libraryapi.service.LoanService", "com.luiz.libraryapi.service.EmailService" })


public class LibraryApiApplication  {


	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}
	

	
}
