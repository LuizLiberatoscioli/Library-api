package com.luiz.libraryapi.api.resouce;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/Novo")
public class NovoController {

	 @GetMapping
	 public int novo () {
		 int a = 10;
		 return a;
	 }
	
}
