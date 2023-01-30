package com.luiz.libraryapi.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

import com.luiz.libraryapi.model.entity.Book;
import com.luiz.libraryapi.model.entity.Loan;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LoanRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private LoanRepository repository;

	@Test
	@DisplayName("Deve verificar se exsite emprestimo nao devolvido para o livro.")
	public void existByBookAndNotReturnedTest() {
		//cenario
		
		Loan loan = createAndPersistLoan();
		Book book = loan.getBook();
		
		//execucao
		boolean exists = repository.existsByBookAndNotReturned(book);
		
		assertThat(exists).isTrue();
		
	}
	
	  @Test
	    @DisplayName("Deve buscar empréstimo pelo isbn do livro ou customer")
	    public void findByBookIsbnOrCustomerTest(){
	        Loan loan = createAndPersistLoan();

	        Page<Loan> result = repository.findByBookIsbnOrCustomer(
	                "123", "Fulano", PageRequest.of(0, 10));

	        assertThat(result.getContent()).hasSize(1);
	        assertThat(result.getContent()).contains(loan);
	        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
	        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
	        assertThat(result.getTotalElements()).isEqualTo(1);
	    }
	
	public Loan createAndPersistLoan() {
		Book book = createNewBook("123");
		entityManager.persist(book);
		
		Loan loan = Loan.builder().book(book).customer("Fulano").loanDate(LocalDate.now()).build();
		entityManager.persist(loan);
		
		return loan;
	}
	
	private Book createNewBook(String isbn) {
		return Book.builder().author("Fulano").title("as aventuras").isbn(isbn).build();
	}
}
