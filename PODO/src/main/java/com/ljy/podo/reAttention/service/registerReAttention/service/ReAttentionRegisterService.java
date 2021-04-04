package com.ljy.podo.reAttention.service.registerReAttention.service;

import java.util.UUID;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.attention.infrastructure.AttentionRepository;
import com.ljy.podo.common.RegisterService;
import com.ljy.podo.common.Validator;
import com.ljy.podo.reAttention.ReAttentionId;
import com.ljy.podo.reAttention.aggregate.exception.ReAttentionNotFoundException;
import com.ljy.podo.reAttention.infrastructure.ReAttentionRepository;
import com.ljy.podo.reAttention.service.registerReAttention.RegisterReAttention;

public class ReAttentionRegisterService extends RegisterService<RegisterReAttention> {

	private ReAttentionRepository reAttentionRepository;
	private AttentionRepository attentionRepository;
	
	@Override
	protected void afterValidation(RegisterReAttention obj) {
		attentionRepository.findById(new AttentionId(obj.getAttentionId())).orElseThrow(()->new ReAttentionNotFoundException("존재하지 않는 조언글입니다. 조언글을 다시 확인해주세요.","attention"));
	}
	
	@Override
	protected void reigsterEntity(RegisterReAttention obj) {
		reAttentionRepository.save(obj.toEntity(createId()));
	}

	private ReAttentionId createId() {
		return new ReAttentionId(UUID.randomUUID().toString());
	}
	
	public ReAttentionRegisterService(Validator<RegisterReAttention> validate, ReAttentionRepository reAttentionRepository,
			AttentionRepository attentionRepository) {
		super(validate);
		this.attentionRepository = attentionRepository;
		this.reAttentionRepository = reAttentionRepository;
	}

}
