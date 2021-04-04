package com.ljy.podo.reAttention.service.updateReAttention.service;

import com.ljy.podo.common.RegisterService;
import com.ljy.podo.common.Validator;
import com.ljy.podo.portfolio.Writer;
import com.ljy.podo.reAttention.ReAttentionId;
import com.ljy.podo.reAttention.aggregate.ReAttention;
import com.ljy.podo.reAttention.aggregate.exception.InvalidReAttentionException;
import com.ljy.podo.reAttention.aggregate.exception.ReAttentionNotFoundException;
import com.ljy.podo.reAttention.infrastructure.ReAttentionRepository;
import com.ljy.podo.reAttention.service.updateReAttention.UpdateReAttention;

public class ReAttentionUpdateService extends RegisterService<UpdateReAttention> {

	private ReAttentionRepository reAttentionRepository;

	@Override
	protected void afterValidation(UpdateReAttention obj) {
		ReAttention reAttention = reAttentionRepository.findById(new ReAttentionId(obj.getReAttentionId())).orElseThrow(()->new ReAttentionNotFoundException("해당 조언 답글이 존재하지 않습니다.","reAttention"));
		verfyIsMyReAttention(obj, reAttention);
		reAttention.update(obj);
	}
	
	@Override
	protected void reigsterEntity(UpdateReAttention obj) {
	}

	private void verfyIsMyReAttention(UpdateReAttention obj, ReAttention reAttention) {
		if (!reAttention.getWriter().equals(new Writer(obj.getUpdater()))) {
			throw new InvalidReAttentionException("자신의 조언 답글만 수정할 수 있습니다.", "reAttention");
		}
	}
	
	public ReAttentionUpdateService(Validator<UpdateReAttention> validate, ReAttentionRepository reAttentionRepository) {
		super(validate);
		this.reAttentionRepository = reAttentionRepository;
	}

}
