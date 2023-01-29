package com.luiz.libraryapi.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luiz.libraryapi.api.dto.LoanFilterDTO;
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

	@Override
	public Optional<Loan> getById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Loan update(Loan loan) {
		return repository.save(loan);
	}

	@Override
	public Page<Loan> find(LoanFilterDTO filterDTO, Pageable pageable) {
		return repository.findByBookIsbnOrCustomer(filterDTO.getIsbn(), filterDTO.getCustomer(), pageable);
	}

}
