package com.luiz.libraryapi.resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")  
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {
	
	//rota
	static String BOOK_API = "/api/books";
	
	@Autowired
	MockMvc mvc;
	
	@Test
	@DisplayName ("Deve criar um livro com sucesso.")
	public void createBookTest() throws Exception  {
		
		String json = new ObjectMapper().writeValueAsString(null);	//criar o json , para nao precisar escrever ele todo na string
		
		//montar a requisicao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders		
		.post(BOOK_API) //tipo requisicao
		.contentType(MediaType.APPLICATION_JSON)  	//conteudo
		.accept(MediaType.APPLICATION_JSON) 		// o servidor aceita tipo json
		.content(json);								// passar o json (corpo da requiscao , dados do livro)
		
		//fazer a requisicao
		mvc							
		.perform(request)	// recebe a requisicao q preparamos
		.andExpect(MockMvcResultMatchers.status().isCreated())				//mandar os verificadores
		.andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())		//testar o json de resposta com os dados 
		.andExpect(MockMvcResultMatchers.jsonPath("title").value("Meu livro"))
		.andExpect(MockMvcResultMatchers.jsonPath("author").value("Autor"))
		.andExpect(MockMvcResultMatchers.jsonPath("isbn").value("123456"))
		;
		
	}
	
	@Test
	@DisplayName ("Deve lancar erro de validacao quando nao houver dados suficientes para criacao do livro")
	public void createInvalidTest() {
		
	}

}
