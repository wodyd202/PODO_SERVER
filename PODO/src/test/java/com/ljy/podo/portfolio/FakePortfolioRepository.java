package com.ljy.podo.portfolio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;
import com.ljy.podo.portfolio.service.loadPortfolio.PortfolioSearchDTO;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioFullData;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioListData;

public class FakePortfolioRepository implements PortfolioRepository {
	
	private final Map<String, Portfolio> repository = new HashMap<>();
	
	@Override
	public void save(Portfolio portfolio) {
		repository.put(portfolio.getId().toString(), portfolio);
	}

	@Override
	public Optional<Portfolio> findById(PortfolioId portfolioId) {
		return Optional.ofNullable(repository.get(portfolioId.getValue()));
	}

	@Override
	public List<PortfolioListData> findAll(PortfolioSearchDTO searchDTO) {
		return null;
	}

	@Override
	public long countAll(PortfolioSearchDTO searchDTO) {
		return 0;
	}

	@Override
	public Optional<PortfolioFullData> findPortfolioViewDataById(PortfolioId portfolioId) {
		return null;
	}
}
