package com.ljy.podo.portfolio.service.util;

import com.ljy.podo.common.Validator;
import com.ljy.podo.portfolio.PortfolioState;
import com.ljy.podo.portfolio.ShowType;
import com.ljy.podo.portfolio.aggregate.exception.InvalidPortfolioException;
import com.ljy.podo.portfolio.service.registerPortfolio.RegisterPortfolio;

public class RegisterPortfolioValidator implements Validator<RegisterPortfolio>{

	@Override
	public void validate(RegisterPortfolio obj) {
		String title = obj.getTitle();
		String content = obj.getContent();
		String header = obj.getHeader();
		ShowType showType = obj.getShowType();
		PortfolioState state = obj.getState();
		verfyNotEmptyStringValue(title, "포트폴리오 제목을 입력해주세요.", "title");
		verfyNotEmptyStringValue(content, "포트폴리오 내용을 입력해주세요.", "content");
		verfyNotEmptyStringValue(header, "포트폴리오 주제를 입력해주세요.", "header");
		verfyNotNullState(state);
		verfyNotNullShowType(showType);
	}

	private void verfyNotNullShowType(ShowType showType) {
		if(showType == null) {
			throw new InvalidPortfolioException("공개 타입을 입력해주세요","showType");
		}
	}

	private void verfyNotNullState(PortfolioState state) {
		if(state == null) {
			throw new InvalidPortfolioException("등록 타입을 입력해주세요","state");
		}
	}
	
	private void verfyNotEmptyStringValue(String value, String msg, String field) {
		if (value == null || value.isEmpty()) {
			throw new InvalidPortfolioException(msg, field);
		}
	}

}
