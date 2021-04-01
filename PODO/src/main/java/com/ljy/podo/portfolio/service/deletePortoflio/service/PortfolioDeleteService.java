package com.ljy.podo.portfolio.service.deletePortoflio.service;

import org.springframework.transaction.annotation.Transactional;

import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.Writer;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.aggregate.exception.InvalidPortfolioException;
import com.ljy.podo.portfolio.aggregate.exception.PortfolioNotFindException;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;
import com.ljy.podo.portfolio.service.deletePortoflio.DeletePortfolio;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PortfolioDeleteService {
	private final PortfolioRepository portfolioRepository;

	@Transactional
	public void delete(DeletePortfolio deletePortfolio) {
		Portfolio portfolio = portfolioRepository.findById(new PortfolioId(deletePortfolio.getId()))
				.orElseThrow(() -> new PortfolioNotFindException("존재하지 않는 포트폴리오 입니다.", "portfolio"));
		verfyIsMyPortfolio(deletePortfolio, portfolio);
		portfolio.delete();
	}
	
	private void verfyIsMyPortfolio(DeletePortfolio obj, Portfolio portfolio) {
		if (!portfolio.getWriter().equals(new Writer(obj.getDeleter()))) {
			throw new InvalidPortfolioException("자신의 포트폴리오만 삭제할 수 있습니다.", "portfolio");
		}
	}
}
