package com.luiz.libraryapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luiz.libraryapi.model.entity.Book;

public interface BookRepository extends JpaRepository<Book , Long>{

}
