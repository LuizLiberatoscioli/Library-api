package com.luiz.libraryapi.bookService.Impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.luiz.libraryapi.exception.BusinessException;
import com.luiz.libraryapi.model.entity.Book;
import com.luiz.libraryapi.model.repository.BookRepository;
import com.luiz.libraryapi.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository repository;
	
	public BookServiceImpl (BookRepository repository) {
		this.repository = repository;
	}

	@Override
	public Book save(Book book) {
		if (repository.existsByIsbn(book.getIsbn())) {
			throw new BusinessException ("Isbn ja cadastrado.");
		}
		return repository.save(book);
	}

	@Override
	public Optional<Book> getById(Long id) {
		return Optional.empty();
	}

	@Override
	public void delete(Book book) {
		
		
	}

	@Override
	public Object update(Book updatingBook) {
		
		return null;
	}
	
	

}
