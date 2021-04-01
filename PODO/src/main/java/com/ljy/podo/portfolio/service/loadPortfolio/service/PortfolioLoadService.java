package com.ljy.podo.portfolio.service.loadPortfolio.service;

import java.util.List;

import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;
import com.ljy.podo.portfolio.service.loadPortfolio.PortfolioSearchDTO;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioListData;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PortfolioLoadService {
	private final PortfolioRepository portfolioRepository;
	
	public List<PortfolioListData> findAll(PortfolioSearchDTO searchDTO) {
		return portfolioRepository.findAll(searchDTO);
	}
	
}
