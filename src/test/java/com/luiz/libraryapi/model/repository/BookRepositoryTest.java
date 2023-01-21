package com.luiz.libraryapi.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luiz.libraryapi.model.entity.Book;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")  
@DataJpaTest
public class BookRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager; 
	
	@Autowired
	BookRepository repository;
	
	@Test
	@DisplayName ("Deve retornar verdadeiro quando existir um livro na base com o isbn informado")
	public void returnTrueWhenIsbnExists() {
		//cenario
		
		String isbn = "123";
		Book book = createNewBook(isbn);
		entityManager.persist(book);
		
		//execucao
		
		boolean exists = repository.existsByIsbn(isbn);
		
		//verificacao
		assertThat(exists).isTrue();
		
	}

	private Book createNewBook(String isbn) {
		return Book.builder().author("Fulano").title("as aventuras").isbn(isbn).build();
	}
	
	@Test
	@DisplayName ("Deve retornar falso quando nao existir um livro na base com o isbn informado")
	public void returnFalseWhenIsbnDoesntExists() {
		//cenario
		
		String isbn = "123";
		
		//execucao
		
		boolean exists = repository.existsByIsbn(isbn);
		
		//verificacao
		assertThat(exists).isFalse();
		
	}
	
	@Test
	@DisplayName ("deve obter um livro por id.")
	public void findByIdTest() {
		//cenario
		Book book = createNewBook("123");
		entityManager.persist(book);
		
		//execucao
		Optional<Book>foundBook = repository.findById(book.getId());
		
		//verificar
		assertThat (foundBook.isPresent()).isTrue();
	}
	
	@Test
	@DisplayName ("deve salvar um livro.")
	public void saveBookTest() {
		Book book = createNewBook("123");
		
		Book saveBook = repository.save(book);
		
		assertThat(saveBook.getId()).isNotNull();
	}
	
	@Test
	@DisplayName ("deve deletar um livro salvo")
	public void deleteBookTest() {
		
		Book book = createNewBook("123");
		entityManager.persist(book);
		
		Book foundbook = entityManager.find(Book.class, book.getId());
		
		repository.delete(foundbook);
		
		Book deletedBook = entityManager.find(Book.class, book.getId());
		
		assertThat(deletedBook).isNull();
		
	}
		
}

