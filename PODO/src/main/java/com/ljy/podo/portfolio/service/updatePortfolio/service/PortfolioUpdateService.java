package com.ljy.podo.portfolio.service.updatePortfolio.service;

import com.ljy.podo.common.RegisterService;
import com.ljy.podo.common.Validator;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.PortfolioState;
import com.ljy.podo.portfolio.Writer;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.aggregate.exception.InvalidPortfolioException;
import com.ljy.podo.portfolio.aggregate.exception.PortfolioNotFindException;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;
import com.ljy.podo.portfolio.service.updatePortfolio.UpdatePortfolio;

public class PortfolioUpdateService extends RegisterService<UpdatePortfolio> {

	private PortfolioRepository portfolioRepository;

	@Override
	protected void afterValidation(UpdatePortfolio obj) {
	}

	@Override
	protected void reigsterEntity(UpdatePortfolio obj) {
		Portfolio portfolio = portfolioRepository.findById(new PortfolioId(obj.getId()))
				.orElseThrow(() -> new PortfolioNotFindException("존재하지 않는 포트폴리오 입니다.", "portfolio"));
		verfyAleadyDeletePortfolio(portfolio);
		verfyIsMyPortfolio(obj, portfolio);
		verfyChangeAbleState(obj, portfolio);
		portfolio.update(obj);
	}

	private void verfyAleadyDeletePortfolio(Portfolio portfolio) {
		if(portfolio.isDelete()) {
			throw new PortfolioNotFindException("존재하지 않는 포트폴리오입니다. 다시 확인해주세요.", "portfolio");
		}
	}
	
	private void verfyChangeAbleState(UpdatePortfolio obj,Portfolio target) {
		if(target.getState() == PortfolioState.CREATE && obj.getState() == PortfolioState.TEMPORARY) {
			throw new InvalidPortfolioException("이미 등록된 포트폴리오를 임시 저장 상태로 바꿀 수 없습니다.", "portfolio");
		}
	}

	private void verfyIsMyPortfolio(UpdatePortfolio obj, Portfolio portfolio) {
		if (!portfolio.getWriter().equals(new Writer(obj.getUpdater()))) {
			throw new InvalidPortfolioException("자신의 포트폴리오만 수정할 수 있습니다.", "portfolio");
		}
	}

	public PortfolioUpdateService(Validator<UpdatePortfolio> validate, PortfolioRepository portfolioRepository) {
		super(validate);
		this.portfolioRepository = portfolioRepository;
	}

}
