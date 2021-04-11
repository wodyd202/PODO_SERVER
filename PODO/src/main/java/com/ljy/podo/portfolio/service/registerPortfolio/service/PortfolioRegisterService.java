package com.ljy.podo.portfolio.service.registerPortfolio.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.ljy.podo.common.RegisterService;
import com.ljy.podo.common.Validator;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.infrastructure.PortfolioElasticsearchRepository;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;
import com.ljy.podo.portfolio.service.registerPortfolio.RegisterPortfolio;

public class PortfolioRegisterService extends RegisterService<RegisterPortfolio> {

	private PortfolioRepository portfolioRepository;
	private PortfolioElasticsearchRepository portfolioElasticsearchRepository;

	@Override
	protected void reigsterEntity(RegisterPortfolio obj) {
		try {
			Portfolio entity = obj.toEntity(createId());
			portfolioRepository.save(entity);
			persistEntityIntoElasticsearch(entity);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	private void persistEntityIntoElasticsearch(Portfolio entity) throws IOException {
		if (portfolioElasticsearchRepository != null) {
			portfolioElasticsearchRepository.save(entity);
		}
	}

	private PortfolioId createId() {
		return new PortfolioId(UUID.randomUUID().toString());
	}

	public PortfolioRegisterService(Validator<RegisterPortfolio> validate, PortfolioRepository portfolioRepository) {
		super(validate);
		this.portfolioRepository = portfolioRepository;
	}

	public PortfolioRegisterService(Validator<RegisterPortfolio> validate, PortfolioRepository portfolioRepository,
			PortfolioElasticsearchRepository portfolioElasticsearchRepository) {
		super(validate);
		this.portfolioRepository = portfolioRepository;
		this.portfolioElasticsearchRepository = portfolioElasticsearchRepository;
	}
}
