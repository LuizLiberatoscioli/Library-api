package com.luiz.libraryapi.bookService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
		
		//cenario
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
	@Test
	@DisplayName ("Deve deletar o livro.")
	public void bookDeleteTest() {
		//cenario 
		Long id = 1l;
		Book book = createValidBook();
		book.setId(id);
		
		//execucao
		org.junit.jupiter.api.Assertions.assertDoesNotThrow( () -> service.delete(book)); 
		
		//verificacao
		Mockito.verify(repository ,Mockito.times(1) ).delete(book);
		
	}
	@Test
	@DisplayName ("Não pode apagar book inexistente.")
	public void nullBookDeleteTest() {
	
		Book book = new Book();
	
		//execucao
		org.junit.jupiter.api.Assertions.assertThrows( IllegalArgumentException.class,() -> service.delete(book)); 
		
		//verificacao
		Mockito.verify(repository ,Mockito.times(0) ).delete(book);
	}
	
	@Test
	@DisplayName ("deve fazer update do book")
	public void bookUpdateTest() {
		Long id = 1l;
		Book updatingBook = createValidBook();
		updatingBook.setId(id);
		
		//simulacao
		Book updatedBook = createValidBook();
		updatedBook.setId(id);
		
		Mockito.when(repository.save(updatingBook)).thenReturn(updatedBook);
		//execucao
		Book book = service.update(updatedBook);
		
		
		//verificacao
		assertThat(book.getId()).isEqualTo(updatedBook.getId());
		assertThat(book.getAuthor()).isEqualTo(updatedBook.getAuthor());
		assertThat(book.getIsbn()).isEqualTo(updatedBook.getIsbn());
		assertThat(book.getTitle()).isEqualTo(updatedBook.getTitle());
	}
	
	@Test
	@DisplayName ("não deve fazer update do book se id is null")
	public void nullBookUpdateTest() {
		
		Book book = new Book();
		
		//execucao
		org.junit.jupiter.api.Assertions.assertThrows( IllegalArgumentException.class,() -> service.update(book)); 
		
		//verificacao
		Mockito.verify(repository ,Mockito.times(0) ).save(book);
	}
	
	@Test
	@DisplayName ("Deve filtrar livros pelas propriedades")
	public void findBookTest () {
		
	       //cenario
        Book book = createValidBook();

        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Book> lista = Arrays.asList(book);
        Page<Book> page = new PageImpl<Book>(lista, pageRequest, 1);
        when( repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);

        //execucao
        Page<Book> result = service.find(book, pageRequest);


        //verificacoes
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(lista);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
		
	}
}
