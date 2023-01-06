package com.luiz.libraryapi.bookService;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luiz.libraryapi.bookService.Impl.BookServiceImpl;
import com.luiz.libraryapi.model.entity.Book;
import com.luiz.libraryapi.model.repository.BookRepository;
import com.luiz.libraryapi.service.BookService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")  
public class BookServiceTest {

	BookService service;
	
	@MockBean
	private BookRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.service = new BookServiceImpl( repository);
	}
	
	@Test
	@DisplayName("Deve salvar um livro")
	public void saveBookTest () {
		//cenario
		Book book = Book.builder().author("Fulano").title("as aventuras").isbn("123").build();
		Mockito.when( repository.save(book))
		.thenReturn(Book.builder()
				.id(1l)
				.author("Fulano")
				.title("as aventuras")
				.isbn("123")
				.build());
		
		//execucao
		Book savedBook= service.save(book);
		
		//verificacao
		assertThat(savedBook.getId()).isNotNull();
		assertThat(savedBook.getIsbn()).isEqualTo("123");
		assertThat(savedBook.getTitle()).isEqualTo("as aventuras");
		assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
	}
}
