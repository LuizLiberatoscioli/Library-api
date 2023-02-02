package com.luiz.libraryapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	void sendMails(String mensage, List<String> mailsList);

}
