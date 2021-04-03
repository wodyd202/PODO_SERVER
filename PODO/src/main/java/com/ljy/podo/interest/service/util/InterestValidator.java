package com.ljy.podo.interest.service.util;

import com.ljy.podo.common.Validator;
import com.ljy.podo.interest.service.registerInterest.RegisterInterest;
import com.ljy.podo.portfolio.aggregate.exception.PortfolioNotFindException;

public class InterestValidator implements Validator<RegisterInterest> {

	@Override
	public void validate(RegisterInterest obj) {
		verfyNotEmptyPortfolioId(obj.getPortfolioId());
	}

	private void verfyNotEmptyPortfolioId(String portfolioId) {
		if (portfolioId == null || portfolioId.isEmpty()) {
			throw new PortfolioNotFindException("포트폴리오 고유번호를 입력해주세요.", "portfolio");
		}
	}
}
