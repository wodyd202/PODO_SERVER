package com.ljy.podo.portfolio.service.registerPortfolio.service;

import java.util.UUID;

import com.ljy.podo.common.RegisterService;
import com.ljy.podo.common.Validator;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;
import com.ljy.podo.portfolio.service.registerPortfolio.RegisterPortfolio;

public class PortfolioRegisterService extends RegisterService<RegisterPortfolio> {

	private PortfolioRepository portfolioRepository;

	@Override
	protected void reigsterEntity(RegisterPortfolio obj) {
		portfolioRepository.save(obj.toEntity(createId()));
	}

	private PortfolioId createId() {
		return new PortfolioId(UUID.randomUUID().toString());
	}

	public PortfolioRegisterService(Validator<RegisterPortfolio> validate, PortfolioRepository portfolioRepository) {
		super(validate);
		this.portfolioRepository = portfolioRepository;
	}
}
