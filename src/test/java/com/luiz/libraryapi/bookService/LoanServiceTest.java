package com.luiz.libraryapi.bookService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luiz.libraryapi.exception.BusinessException;
import com.luiz.libraryapi.model.entity.Book;
import com.luiz.libraryapi.model.entity.Loan;
import com.luiz.libraryapi.model.repository.LoanRepository;
import com.luiz.libraryapi.service.BookService;
import com.luiz.libraryapi.service.LoanService;
import com.luiz.libraryapi.service.impl.LoanServiceImpl;

@ExtendWith (SpringExtension.class)
@ActiveProfiles ("test")
public class LoanServiceTest {
	
	LoanService service;
	
	@MockBean
	LoanRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.service = new LoanServiceImpl(repository);
	}

	@Test
	@DisplayName ("Deve salvar um empréstimo")
	public void saveLoanTest() {
		
		Book book = Book.builder().id(1l).build();
		
		Loan savingLoan = Loan.builder()
				.book(book)
				.customer("Fulano")
				.loanDate(LocalDate.now())
				.build()
				;
		
		Loan savedLoan = Loan.builder()
				.id(null)
				.loanDate(LocalDate.now())
				.customer("Fulano")
				.book(book)
				.build();
		
		when (repository.existsByBookAndNotReturned(book)).thenReturn(false);
		when(repository.save(savingLoan)).thenReturn(savedLoan);
		
		Loan loan = service.save(savedLoan);
		
		assertThat(loan.getId()).isEqualTo(savedLoan.getId());
		assertThat(loan.getBook().getId()).isEqualTo(savedLoan.getBook().getId());
		assertThat(loan.getCustomer()).isEqualTo(savedLoan.getCustomer());
		assertThat(loan.getLoanDate()).isEqualTo(savedLoan.getLoanDate());	
	}
	
	@Test
	@DisplayName ("Deve lancar erro de negocio ao salvar um emprestimo com livro ja emprestado")
	public void loanedBookSaveTest() {
		
		Book book = Book.builder().id(1l).build();
		String customer = "Fulano";
		
		Loan savingLoan = Loan.builder()
				.book(book)
				.customer("Fulano")
				.loanDate(LocalDate.now())
				.build();
			
		when (repository.existsByBookAndNotReturned(book)).thenReturn(true);
		
		Throwable exception = catchThrowable( () ->  service.save(savingLoan)) ;
		
		assertThat(exception).isInstanceOf(BusinessException.class)
		.hasMessage("Book already loaned")
		;
		
		verify(repository , never()).save(savingLoan);
	}
	
	
}
