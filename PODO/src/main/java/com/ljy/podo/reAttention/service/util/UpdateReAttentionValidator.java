package com.ljy.podo.reAttention.service.util;

import com.ljy.podo.common.Validator;
import com.ljy.podo.reAttention.aggregate.exception.InvalidReAttentionException;
import com.ljy.podo.reAttention.aggregate.exception.ReAttentionNotFoundException;
import com.ljy.podo.reAttention.service.updateReAttention.UpdateReAttention;

public class UpdateReAttentionValidator implements Validator<UpdateReAttention>{

	@Override
	public void validate(UpdateReAttention obj) {
		String reAttentionId = obj.getReAttentionId();
		String content = obj.getContent();
		
		verfyNotEmptyReAttentionId(reAttentionId);
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

	private void verfyNotEmptyReAttentionId(String reAttentionId) {
		if(reAttentionId == null || reAttentionId.isEmpty()) {
			throw new ReAttentionNotFoundException("조언 답글 고유번호를 입력해주세요.","attention");
		}
	}
	
}
