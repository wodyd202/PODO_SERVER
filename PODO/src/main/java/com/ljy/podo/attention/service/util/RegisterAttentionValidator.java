package com.ljy.podo.attention.service.util;

import com.ljy.podo.attention.aggregate.exception.InvalidAttentionException;
import com.ljy.podo.attention.service.registerAttention.RegisterAttention;
import com.ljy.podo.common.Validator;

public class RegisterAttentionValidator implements Validator<RegisterAttention> {

	@Override
	public void validate(RegisterAttention obj) {
		String portfolioId = obj.getPortfolioId();
		String content = obj.getContent();

		verfyNotEmptyStringValue(portfolioId, "포트폴리오를 입력해주세요.", "portfolioId");
		verfyNotEmptyStringValue(content, "조언 내용을 입력해주세요.", "content");
		verfy1MoreAnd100orLessContent(content);
	}

	private void verfy1MoreAnd100orLessContent(String content) {
		if (content.length() < 1 || content.length() > 100) {
			throw new InvalidAttentionException("조언 내용은 1자 이상 100자 이하로 입력해주세요.", "content");
		}
	}

	private void verfyNotEmptyStringValue(String value, String msg, String field) {
		if (value == null || value.isEmpty()) {
			throw new InvalidAttentionException(msg, field);
		}
	}
}
