package com.luiz.libraryapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.luiz.libraryapi.api.dto.LoanFilterDTO;
import com.luiz.libraryapi.model.entity.Book;
import com.luiz.libraryapi.model.entity.Loan;

@Service
public interface LoanService {
	   Loan save( Loan loan );

	    Optional<Loan> getById(Long id);

	    Loan update(Loan loan);

	   Page<Loan> find(LoanFilterDTO filterDTO, Pageable pageable);

	   Page<Loan> getLoansByBook( Book book, Pageable pageable);

	   List<Loan> getAllLateLoans();

}
