package com.ljy.podo.reAttention.service.util;

import com.ljy.podo.attention.aggregate.exception.AttentionNotFoundException;
import com.ljy.podo.common.Validator;
import com.ljy.podo.reAttention.aggregate.exception.InvalidReAttentionException;
import com.ljy.podo.reAttention.service.registerReAttention.RegisterReAttention;

public class RegisterReAttentionValidator implements Validator<RegisterReAttention>{

	@Override
	public void validate(RegisterReAttention obj) {
		String attentionId = obj.getAttentionId();
		String content = obj.getContent();
		verfyNotEmptyAttentionId(attentionId);
		verfyNotEmptyContent(content);
		verfyGte1AndLte100Content(content);
	}
	
	private void verfyGte1AndLte100Content(String content) {
		if(content.length() <= 0 || content.length() > 100) {
			throw new InvalidReAttentionException("조언 답글은 1자 이상 100자 이하로 입력해주세요.","content");
		}
	}
	
	private void verfyNotEmptyContent(String content) {
		if(content == null || content.isEmpty()) {
			throw new InvalidReAttentionException("조언 답글은 1자 이상 100자 이하로 입력해주세요.","content");
		}
	}

	private void verfyNotEmptyAttentionId(String attentionId) {
		if(attentionId == null || attentionId.isEmpty()) {
			throw new AttentionNotFoundException("조언 고유번호를 입력해주세요.","attention");
		}
	}
}
