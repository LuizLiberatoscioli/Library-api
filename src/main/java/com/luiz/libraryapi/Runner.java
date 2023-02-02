package com.luiz.libraryapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;

import com.luiz.libraryapi.service.EmailService;

public class Runner implements CommandLineRunner{
	
	private EmailService emailService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Commando line");
		
	//	  List<String> emails = Arrays.asList("b721d2ab1c-5a5208@inbox.mailtrap.io");
	//	  emailService.sendMails("Testando serviço de emails.", emails);
		  System.out.println("Emails enviados");
		 
		
	}

}
