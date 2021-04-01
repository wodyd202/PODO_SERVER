package com.ljy.podo.attention.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ljy.podo.attention.infrastructure.SimpleAttentionRepository;
import com.ljy.podo.attention.service.deleteAttention.service.AttentionDeleteService;
import com.ljy.podo.attention.service.registerAttention.service.AttentionRegisterService;
import com.ljy.podo.attention.service.updateAttention.service.AttentionUpdateService;
import com.ljy.podo.attention.service.util.RegisterAttentionValidator;
import com.ljy.podo.attention.service.util.UpdateAttentionValidator;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;

@Configuration
public class AttentionConfig {
	
	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private SimpleAttentionRepository attentionRepository;
	
	@Bean
	public RegisterAttentionValidator registerAttentionValidator() {
		return new RegisterAttentionValidator();
	}

	@Bean
	public UpdateAttentionValidator updateAttentionValidator() { 
		return new UpdateAttentionValidator();
	}
	
	@Bean
	public AttentionRegisterService attentionRegisterService() {
		return new AttentionRegisterService(registerAttentionValidator(), portfolioRepository, attentionRepository);
	}
	
	@Bean
	public AttentionUpdateService attentionUpdateService() {
		return new AttentionUpdateService(updateAttentionValidator(), attentionRepository);
	}
	
	@Bean
	public AttentionDeleteService attentionDeleteService() {
		return new AttentionDeleteService(attentionRepository);
	}
}
