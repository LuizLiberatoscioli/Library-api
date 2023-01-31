package com.luiz.libraryapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.luiz.libraryapi.model.entity.Loan;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

	public static final String CRON_LATE_LOANS = "0 0 0 1/1 * ?" ;
	
	@Value("${application.email.lateloans.message}")
	private String mensage;
	
	private final LoanService loanService;
	private final EmailService emailService;
	
	@Scheduled (cron = CRON_LATE_LOANS)
	public void sendMailToLateLoans() {
		List<Loan> allLateLoans = loanService.getAllLateLoans();
		
		List<String> mailsList =
		allLateLoans.stream()
		.map( loan -> loan.getCustomerEmail())
		.collect(Collectors.toList());
			
		emailService.sendMails(mensage , mailsList);
	}
}
