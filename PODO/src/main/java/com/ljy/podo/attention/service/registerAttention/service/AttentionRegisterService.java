package com.ljy.podo.attention.service.registerAttention.service;

import java.util.UUID;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.attention.aggregate.exception.InvalidAttentionException;
import com.ljy.podo.attention.infrastructure.AttentionRepository;
import com.ljy.podo.attention.service.registerAttention.RegisterAttention;
import com.ljy.podo.common.RegisterService;
import com.ljy.podo.common.Validator;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.Writer;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.aggregate.exception.PortfolioNotFindException;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;

public class AttentionRegisterService extends RegisterService<RegisterAttention> {
	private PortfolioRepository portfolioRepository;
	private AttentionRepository attentionRepository;

	@Override
	protected void afterValidation(RegisterAttention obj) {
		Portfolio portfolio = portfolioRepository.findById(new PortfolioId(obj.getPortfolioId()))
				.orElseThrow(() -> new PortfolioNotFindException("포트폴리오가 존재하지 않습니다. 다시 확인해주세요.", "portfolio"));
		verfyIsMyPortfolio(obj, portfolio);
	}

	@Override
	protected void reigsterEntity(RegisterAttention obj) {
		attentionRepository.save(obj.toEntity(createId()));
	}

	private void verfyIsMyPortfolio(RegisterAttention obj, Portfolio portfolio) {
		if (portfolio.getWriter().equals(new Writer(obj.getWriter()))) {
			throw new InvalidAttentionException("자신의 포트폴리오에 조언을 등록할 수 없습니다.", "portfolio");
		}
	}
	
	private AttentionId createId() {
		return new AttentionId(UUID.randomUUID().toString());
	}

	public AttentionRegisterService(Validator<RegisterAttention> validate, PortfolioRepository portfolioRepository,
			AttentionRepository attentionRepository) {
		super(validate);
		this.portfolioRepository = portfolioRepository;
		this.attentionRepository = attentionRepository;
	}

}
