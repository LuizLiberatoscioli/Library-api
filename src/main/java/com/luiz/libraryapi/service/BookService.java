package com.luiz.libraryapi.service;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.luiz.libraryapi.model.entity.Book;

public interface BookService {

	Book save(Book any);

	Optional<Book> getById(Long id);

	void delete(Book book);

	Book update(Book book);

	Page<Book> find(Book filter, org.springframework.data.domain.Pageable pageRequest);
}
