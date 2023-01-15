package com.luiz.libraryapi.bookService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luiz.libraryapi.bookService.Impl.BookServiceImpl;
import com.luiz.libraryapi.exception.BusinessException;
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
		Book book = createValidBook();
		Mockito.when(repository.existsByIsbn(Mockito.anyString()) ).thenReturn(false);
		
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

	private Book createValidBook() {
		return Book.builder().author("Fulano").title("as aventuras").isbn("123").build();
	}
	
	@Test
	@DisplayName ("Deve lancar erro de negocio ao tentar salvar um livro com isbn duplicado")
	public void shouldNotSaveABookWithDuplicatedISBN() {
		//cenario
		Book book = createValidBook();
		Mockito.when(repository.existsByIsbn(Mockito.anyString()) ).thenReturn(true);
		
		//execucao
		 Throwable exception = Assertions.catchThrowable(() -> service.save(book) );
		 
		 //verificacoes
		 assertThat(exception).isInstanceOf(BusinessException.class)
		 .hasMessage("Isbn ja cadastrado.");
		 
		 Mockito.verify(repository , Mockito.never()).save(book);
		
	}
	
	@Test
	@DisplayName ("Deve obter um livro por ID")
	public void getByIdTest() {
		Long id = 1l;
		Book book =createValidBook();
		book.setId(id);
		
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(book));
		
		//execucao
		Optional<Book> foundBook = service.getById(id);
		
		//verificacao
		assertThat( foundBook.isPresent() ).isTrue();
		assertThat( foundBook.get().getId() ).isEqualTo(id);
		assertThat( foundBook.get().getAuthor() ).isEqualTo(book.getAuthor());
		assertThat( foundBook.get().getTitle() ).isEqualTo(book.getTitle());
		assertThat( foundBook.get().getIsbn() ).isEqualTo(book.getIsbn());
	}
	
	@Test
	@DisplayName ("Deve retornar vazio ao obter um livro por Id quando não existe na base.")
	public void bookNotFoundByIdTest() {
		Long id = 1l;
		
		Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
		
		//execucao
		Optional<Book> book = service.getById(id);
		
		//verificacao
		assertThat( book.isPresent() ).isFalse();
		
	}
	
}
