package com.ljy.podo.portfolio.service.loadPortfolio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.infrastructure.SimplePortfolioRepository;
import com.ljy.podo.portfolio.service.loadPortfolio.PortfolioSearchDTO;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioFullData;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioListData;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PortfolioLoadService {
	private final SimplePortfolioRepository portfolioRepository;
	
	@Transactional(readOnly = true)
	public List<PortfolioListData> findAll(PortfolioSearchDTO searchDTO) {
		return portfolioRepository.findAll(searchDTO);
	}
	
	@Transactional(readOnly = true)
	public Long countAll(PortfolioSearchDTO searchDTO) {
		return portfolioRepository.countAll(searchDTO);
	}

	@Transactional(readOnly = true)
	public Optional<PortfolioFullData> findPortfolioViewDataById(PortfolioId portfolioId) {
		return portfolioRepository.findPortfolioViewDataById(portfolioId);
	}
	
}
