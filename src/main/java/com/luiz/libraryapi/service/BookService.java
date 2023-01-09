package com.luiz.libraryapi.service;

import java.util.Optional;

import com.luiz.libraryapi.model.entity.Book;

public interface BookService {

	Book save(Book any);

	Optional<Book> getById(Long id);

	void delete(Book book);

	Object update(Book updatingBook);
}
