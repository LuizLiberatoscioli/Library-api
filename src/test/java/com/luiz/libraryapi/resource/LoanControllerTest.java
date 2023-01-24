package com.luiz.libraryapi.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luiz.libraryapi.api.resoucer.LoanController;
import com.luiz.libraryapi.dto.LoanDTO;
import com.luiz.libraryapi.model.entity.Book;
import com.luiz.libraryapi.model.entity.Loan;
import com.luiz.libraryapi.service.BookService;
import com.luiz.libraryapi.service.LoanService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")  
@WebMvcTest(controllers = LoanController.class)
@AutoConfigureMockMvc
public class LoanControllerTest {
	
	static final String LOAN_API = "/api/loans";

	@Autowired
	MockMvc mvc;
	
	@MockBean
	private BookService bookService;
	
	@MockBean
	private LoanService loanService;
	
	@Test
	@DisplayName ("Deve realizar um emprestim")
	public void createLoanTest() throws Exception {
		
		LoanDTO dto = LoanDTO.builder().isbn("123").customer("Fulano").build();
		String json = new ObjectMapper().writeValueAsString(dto);
		
		Book book = Book.builder().id(1l).isbn("123").build();
		BDDMockito.given(bookService.getBookByIsbn("123"))
		.willReturn(Optional.of(book)) ;
		
		
		Loan loan = Loan.builder().id(1l).customer("Fulano").book(book).loanDate(LocalDate.now()).build();
		BDDMockito.given(loanService.save(Mockito.any(Loan.class)) ).willReturn(loan);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post( LOAN_API )
		.accept(org.springframework.http.MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.content(json);
		
		mvc.perform(request)
		.andExpect ( status ().isCreated())
		.andExpect(jsonPath ("id").value(1l));
		
	}
	
	
}
