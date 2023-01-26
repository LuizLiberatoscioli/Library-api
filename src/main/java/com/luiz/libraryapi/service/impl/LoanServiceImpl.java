package com.luiz.libraryapi.service.impl;

import com.luiz.libraryapi.exception.BusinessException;
import com.luiz.libraryapi.model.entity.Loan;
import com.luiz.libraryapi.model.repository.LoanRepository;
import com.luiz.libraryapi.service.LoanService;

public class LoanServiceImpl implements LoanService{

	private LoanRepository repository;
	
	public LoanServiceImpl (LoanRepository repository) {
		this.repository = repository;
	}

	@Override
	public Loan save(Loan loan) {
		if (repository.existsByBookAndNotReturned (loan.getBook()) ) {
			throw new BusinessException ("Book already loaned");
		}
		return repository.save(loan);
	}

}
