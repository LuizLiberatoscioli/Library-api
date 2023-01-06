package com.luiz.libraryapi.bookService.Impl;

import org.springframework.stereotype.Service;

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
		return repository.save(book);
	}

}