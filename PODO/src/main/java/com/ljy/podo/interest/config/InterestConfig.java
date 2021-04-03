package com.ljy.podo.interest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ljy.podo.interest.infrastructure.SimpleInterestRepository;
import com.ljy.podo.interest.service.deleteInterest.service.InterestDeleteService;
import com.ljy.podo.interest.service.registerInterest.service.InterestRegisterService;
import com.ljy.podo.interest.service.util.InterestValidator;
import com.ljy.podo.portfolio.infrastructure.SimplePortfolioRepository;

@Configuration
public class InterestConfig {

	@Autowired
	private SimpleInterestRepository interestRepository;

	@Autowired
	private SimplePortfolioRepository portfolioRepository;
	
	@Bean
	public InterestValidator interestValidator() {
		return new InterestValidator();
	}
	
	@Bean
	public InterestRegisterService interestRegisterService() {
		return new InterestRegisterService(interestValidator(), portfolioRepository, interestRepository);
	}
	
	@Bean
	public InterestDeleteService interestDeleteService() {
		return new InterestDeleteService(interestRepository);
	}
}
