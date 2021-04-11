package com.ljy.podo.portfolio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ljy.podo.portfolio.infrastructure.SimplePortfolioElasticsearchRepository;
import com.ljy.podo.portfolio.infrastructure.SimplePortfolioRepository;
import com.ljy.podo.portfolio.service.deletePortoflio.service.PortfolioDeleteService;
import com.ljy.podo.portfolio.service.loadPortfolio.service.PortfolioLoadService;
import com.ljy.podo.portfolio.service.registerPortfolio.service.PortfolioFileUploadService;
import com.ljy.podo.portfolio.service.registerPortfolio.service.PortfolioRegisterService;
import com.ljy.podo.portfolio.service.updatePortfolio.service.PortfolioUpdateService;
import com.ljy.podo.portfolio.service.util.RegisterPortfolioValidator;
import com.ljy.podo.portfolio.service.util.UpdatePortfolioValidator;

@Configuration
public class PortfolioConfig {
	
	@Autowired
	private SimplePortfolioRepository portfolioRepository;
	
	@Autowired
	private SimplePortfolioElasticsearchRepository portfolioElasticsearchRepository;
	
	@Bean
	public PortfolioFileUploadService portfolioFileUploadService() {
		return new PortfolioFileUploadService();
	}
	
	@Bean
	public RegisterPortfolioValidator registerPortfolioValidator() {
		return new RegisterPortfolioValidator();
	}
	
	@Bean
	public UpdatePortfolioValidator updatePortfolioValidator() {
		return new UpdatePortfolioValidator();
	}
	
	@Bean
	public PortfolioRegisterService portfolioRegisterService() {
		return new PortfolioRegisterService(registerPortfolioValidator(), portfolioRepository, portfolioElasticsearchRepository);
	}
	
	@Bean
	public PortfolioUpdateService portfolioUpdateService() {
		return new PortfolioUpdateService(updatePortfolioValidator(), portfolioRepository, portfolioElasticsearchRepository);
	}
	
	@Bean
	public PortfolioDeleteService portfolioDeleteService() {
		return new PortfolioDeleteService(portfolioRepository, portfolioElasticsearchRepository);
	}
	
	@Bean
	public PortfolioLoadService portfolioLoadService() {
		return new PortfolioLoadService(portfolioRepository);
	}
	
}
