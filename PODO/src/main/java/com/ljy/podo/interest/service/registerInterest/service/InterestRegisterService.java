package com.ljy.podo.interest.service.registerInterest.service;

import java.util.UUID;

import com.ljy.podo.common.RegisterService;
import com.ljy.podo.common.Validator;
import com.ljy.podo.interest.InterestId;
import com.ljy.podo.interest.aggregate.exception.InvalidInterestException;
import com.ljy.podo.interest.infrastructure.InterestRepository;
import com.ljy.podo.interest.service.loadInterest.InterestSearchDTO;
import com.ljy.podo.interest.service.loadInterest.projection.InterestFullData;
import com.ljy.podo.interest.service.registerInterest.RegisterInterest;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.Writer;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.aggregate.exception.PortfolioNotFindException;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;

public class InterestRegisterService extends RegisterService<RegisterInterest> {

	private PortfolioRepository portfolioRepository;
	private InterestRepository interestRepository;

	@Override
	protected void afterValidation(RegisterInterest obj) {
		Portfolio portfolio = portfolioRepository.findById(new PortfolioId(obj.getPortfolioId()))
				.orElseThrow(() -> new PortfolioNotFindException("포트폴리오가 존재하지 않습니다. 다시 확인해주세요.", "portfolio"));
		verfyIsMyPortfolio(obj, portfolio);
		verfyNotExistInterest(obj);
	}

	private void verfyNotExistInterest(RegisterInterest obj) {
		InterestFullData findByPortfolioId = interestRepository.findByPortfolioId(InterestSearchDTO
				.builder()
				.interester(obj.getInterester())
				.portfolioId(obj.getPortfolioId())
				.build());
		if(findByPortfolioId != null) {
			throw new InvalidInterestException("이미 관심도를 등록했습니다.", "interest");
		}
	}
	
	@Override
	protected void reigsterEntity(RegisterInterest obj) {
		interestRepository.save(obj.toEntity(createId()));
	}

	private void verfyIsMyPortfolio(RegisterInterest obj, Portfolio portfolio) {
		if (portfolio.getWriter().equals(new Writer(obj.getInterester()))) {
			throw new InvalidInterestException("자신의 포트폴리오에 관심을 등록할 수 없습니다.", "portfolio");
		}
	}
	
	private InterestId createId() {
		return new InterestId(UUID.randomUUID().toString());
	}

	public InterestRegisterService(Validator<RegisterInterest> validate, PortfolioRepository portfolioRepository,
			InterestRepository interestRepository) {
		super(validate);
		this.portfolioRepository = portfolioRepository;
		this.interestRepository = interestRepository;
	}

}
