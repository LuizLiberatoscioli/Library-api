package com.luiz.libraryapi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luiz.libraryapi.api.dto.LoanFilterDTO;
import com.luiz.libraryapi.model.entity.Book;
import com.luiz.libraryapi.model.entity.Loan;

public interface LoanService {
	   Loan save( Loan loan );

	    Optional<Loan> getById(Long id);

	    Loan update(Loan loan);

	   Page<Loan> find(LoanFilterDTO filterDTO, Pageable pageable);

	   Page<Loan> getLoansByBook( Book book, Pageable pageable);

	   

}
