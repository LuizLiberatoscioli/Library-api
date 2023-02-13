package com.luiz.libraryapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@ComponentScan({ "com.luiz.libraryapi.service.LoanService", "com.luiz.libraryapi.service.EmailService" })
@EnableScheduling
@EnableSwagger2

public class LibraryApiApplication  {
	
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}
	

	
}
