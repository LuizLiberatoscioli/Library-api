package com.luiz.libraryapi;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.luiz.libraryapi.service.EmailService;

@SpringBootApplication
@ComponentScan({"com.luiz.libraryapi.service.LoanService" , "com.luiz.libraryapi.service.EmailService"})
@EnableScheduling

public class LibraryApiApplication {
	
	@Autowired  (required = false) 
	private EmailService emailService;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public CommandLineRunner runner() {
		return args -> {
			List<String> emails = Arrays.asList("b721d2ab1c-5a5208@inbox.mailtrap.io");
			emailService.sendMails("Testando serviço de emails.", emails);
			System.out.println("Emails enviados");
			
		};
		
	}
	

	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}

	

}
