package com.luiz.libraryapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@ComponentScan({"com.luiz.libraryapi.service.LoanService"})
@EnableScheduling

public class LibraryApiApplication {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Scheduled (cron = "0 55 18 1/1 * ? ")
	public void testeAgendamentoTarefas() {
		System.out.println("deu certo");
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}

}
