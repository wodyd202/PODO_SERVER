package com.ljy.podo.portfolio.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.service.loadPortfolio.PortfolioSearchDTO;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioFullData;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioListData;

@Transactional
public interface PortfolioRepository {
	void save(Portfolio portfolio);

	Optional<Portfolio> findById(PortfolioId portfolioId);

	Optional<PortfolioFullData> findPortfolioViewDataById(PortfolioId portfolioId);
	
	List<PortfolioListData> findAll(PortfolioSearchDTO searchDTO);

	long countAll(PortfolioSearchDTO searchDTO);

}
