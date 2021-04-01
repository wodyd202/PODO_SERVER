package com.ljy.podo.attention.service.updateAttention.service;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.attention.aggregate.Attention;
import com.ljy.podo.attention.aggregate.exception.AttentionNotFoundException;
import com.ljy.podo.attention.aggregate.exception.InvalidAttentionException;
import com.ljy.podo.attention.infrastructure.AttentionRepository;
import com.ljy.podo.attention.service.updateAttention.UpdateAttention;
import com.ljy.podo.common.RegisterService;
import com.ljy.podo.common.Validator;
import com.ljy.podo.portfolio.Writer;

public class AttentionUpdateService extends RegisterService<UpdateAttention> {

	private AttentionRepository attentionRepository;

	@Override
	protected void afterValidation(UpdateAttention obj) {
		Attention attention = attentionRepository.findById(new AttentionId(obj.getAttentionId()))
				.orElseThrow(() -> new AttentionNotFoundException("해당 조언이 존재하지 않습니다. 다시 확인해주세요.", "attention"));
		verfyIsMyAttention(obj, attention);
		verfyAleadyDeleteAttention(attention);
		attention.update(obj);
	}

	@Override
	protected void reigsterEntity(UpdateAttention obj) {
	}
	
	private void verfyAleadyDeleteAttention(Attention attention) {
		if(attention.isDelete()) {
			throw new AttentionNotFoundException("존재하지 않는 조언입니다. 다시 확인해주세요.", "attention");
		}
	}
	
	private void verfyIsMyAttention(UpdateAttention obj, Attention attention) {
		if (!attention.getWriter().equals(new Writer(obj.getUpdater()))) {
			throw new InvalidAttentionException("자신의 조언만 수정할 수 있습니다.", "attention");
		}
	}

	public AttentionUpdateService(Validator<UpdateAttention> validate, AttentionRepository attentionRepository) {
		super(validate);
		this.attentionRepository = attentionRepository;
	}

}
