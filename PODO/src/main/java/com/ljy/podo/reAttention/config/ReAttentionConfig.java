package com.ljy.podo.reAttention.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ljy.podo.attention.infrastructure.SimpleAttentionRepository;
import com.ljy.podo.reAttention.infrastructure.SimpleReAttentionRepository;
import com.ljy.podo.reAttention.service.deleteReAttention.service.ReAttentionDeleteService;
import com.ljy.podo.reAttention.service.registerReAttention.service.ReAttentionRegisterService;
import com.ljy.podo.reAttention.service.updateReAttention.service.ReAttentionUpdateService;
import com.ljy.podo.reAttention.service.util.RegisterReAttentionValidator;
import com.ljy.podo.reAttention.service.util.UpdateReAttentionValidator;

@Configuration
public class ReAttentionConfig {
	
	@Autowired
	private SimpleAttentionRepository attentionRepository;
	
	@Autowired
	private SimpleReAttentionRepository reAttentionRepository;
	
	@Bean
	public RegisterReAttentionValidator registerReAttentionValidator() { 
		return new RegisterReAttentionValidator();
	}
	
	@Bean
	public UpdateReAttentionValidator updateReAttentionValidator() { 
		return new UpdateReAttentionValidator();
	}
	
	@Bean
	public ReAttentionRegisterService reAttentionRegisterService() {
		return new ReAttentionRegisterService(registerReAttentionValidator(), reAttentionRepository, attentionRepository);
	}
	
	@Bean
	public ReAttentionUpdateService reAttentionUpdateService() {
		return new ReAttentionUpdateService(updateReAttentionValidator(), reAttentionRepository);
	}
	
	@Bean
	public ReAttentionDeleteService reAttentionDeleteService() {
		return new ReAttentionDeleteService(reAttentionRepository);
	}
}
