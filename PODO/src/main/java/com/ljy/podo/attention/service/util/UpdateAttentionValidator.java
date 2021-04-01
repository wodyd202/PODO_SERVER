package com.ljy.podo.attention.service.util;

import com.ljy.podo.attention.aggregate.exception.InvalidAttentionException;
import com.ljy.podo.attention.service.updateAttention.UpdateAttention;
import com.ljy.podo.common.Validator;

public class UpdateAttentionValidator implements Validator<UpdateAttention> {

	@Override
	public void validate(UpdateAttention obj) {
		String content = obj.getContent();
		String attentionId = obj.getAttentionId();
		verfyNotEmptyStringValue(attentionId, "수정할 조언을 입력해주세요.", "attentionId");
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
