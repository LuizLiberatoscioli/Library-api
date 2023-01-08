package com.luiz.libraryapi.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luiz.libraryapi.api.dto.BookDTO;
import com.luiz.libraryapi.exception.BusinessException;
import com.luiz.libraryapi.model.entity.Book;
import com.luiz.libraryapi.service.BookService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")  
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {
	
	//rota
	static String BOOK_API = "/api/books";
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	BookService service ;
	
	@Test
	@DisplayName ("Deve criar um livro com sucesso.")
	public void createBookTest() throws Exception  {
			
		BookDTO dto = createNewBook();
		Book savedBook = Book.builder().id(10l).author("Arthur").title("as aventuras").isbn("001").build();
		
		BDDMockito.given(service.save(Mockito.any(Book.class)))
			.willReturn(savedBook);		
		String json = new ObjectMapper().writeValueAsString(dto);	//criar o json , para nao precisar escrever ele todo na string
		
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
		.andExpect(jsonPath("id").value(10l))							//testar o json de resposta com os dados 
		 .andExpect( jsonPath("title").value(dto.getTitle()) )
		 .andExpect( jsonPath("author").value(dto.getAuthor()) )
		 .andExpect( jsonPath("isbn").value(dto.getIsbn()) )
		;
		
	}
	
	

	@Test
	@DisplayName ("Deve lancar erro de validacao quando nao houver dados suficientes para criacao do livro")
	public void createInvalidTest() throws Exception {
		
		String json = new ObjectMapper().writeValueAsString(new BookDTO());	
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders		
				.post(BOOK_API) //tipo requisicao
				.contentType(MediaType.APPLICATION_JSON)  	
				.accept(MediaType.APPLICATION_JSON) 		
				.content(json);		
		
		mvc.perform(request)
		.andExpect ( status().isBadRequest() ) 
		.andExpect ( jsonPath ("errors" , Matchers.hasSize(3)));
	}
	
	@Test
	@DisplayName ("Deve lançar erro ao tentar cadastrar um livro com isbn ja utiliado por outro.")
	public void createBookWithDuplicatedIsbn ()  throws Exception{
		
		BookDTO dto = createNewBook();
		
		String json = new ObjectMapper().writeValueAsString(dto);	
		String mensagemErro = "Isbn ja cadastrado."; 
		BDDMockito.given(service.save(Mockito.any(Book.class)))
			.willThrow(new BusinessException ("Isbn ja cadastrado."));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders		
				.post(BOOK_API) //tipo requisicao
				.contentType(MediaType.APPLICATION_JSON)  	
				.accept(MediaType.APPLICATION_JSON) 		
				.content(json);	
		
		mvc.perform(request)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("errors" , Matchers.hasSize(1))) 
		.andExpect(jsonPath ("errors[0]").value(mensagemErro));
	}
	
	private BookDTO createNewBook() {
		return BookDTO.builder().author("Arthur").title("as aventuras").isbn("001").build();
}

}
