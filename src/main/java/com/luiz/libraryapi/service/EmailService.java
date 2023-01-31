package com.luiz.libraryapi.service;

import java.util.List;

public interface EmailService {

	void sendMails(String mensage, List<String> mailsList);

}
