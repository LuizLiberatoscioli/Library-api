package com.luiz.libraryapi.api.resoucer;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.luiz.libraryapi.api.dto.BookDTO;
import com.luiz.libraryapi.model.entity.Book;
import com.luiz.libraryapi.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	private BookService service;
	private ModelMapper modelMapper;
	
	public BookController(BookService service , ModelMapper mapper) {
		this.service = service;
		this.modelMapper = mapper;
	}
	
	@PostMapping
	@ResponseStatus (HttpStatus.CREATED)
	public BookDTO create(@RequestBody BookDTO dto) {
		Book entity = modelMapper.map(dto, Book.class);
		
		service.save(entity);
		
		return modelMapper.map(entity, BookDTO.class);
				
				/*BookDTO.builder()
				.author(entity.getAuthor())
				.title(entity.getTitle())
				.isbn(entity.getIsbn())
				.build();*/
	}

}
