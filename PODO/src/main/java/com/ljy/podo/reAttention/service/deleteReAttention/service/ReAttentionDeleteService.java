package com.ljy.podo.reAttention.service.deleteReAttention.service;

import org.springframework.transaction.annotation.Transactional;

import com.ljy.podo.portfolio.Writer;
import com.ljy.podo.reAttention.ReAttentionId;
import com.ljy.podo.reAttention.aggregate.ReAttention;
import com.ljy.podo.reAttention.aggregate.exception.InvalidReAttentionException;
import com.ljy.podo.reAttention.aggregate.exception.ReAttentionNotFoundException;
import com.ljy.podo.reAttention.infrastructure.ReAttentionRepository;
import com.ljy.podo.reAttention.service.deleteReAttention.DeleteReAttention;

public class ReAttentionDeleteService {

	private ReAttentionRepository reAttentionRepository;
	
	@Transactional
	public void delete(DeleteReAttention deleteReAttention) {
		ReAttention reAttention = reAttentionRepository.findById(new ReAttentionId(deleteReAttention.getReAttentionId()))
				.orElseThrow(()->new ReAttentionNotFoundException("해당 조언 답글이 존재하지 않습니다.","reAttention"));
		verfyIsMyReAttention(deleteReAttention, reAttention);
		verfyIsDeleteReAttention(reAttention);
		reAttention.delete();
	}

	private void verfyIsDeleteReAttention(ReAttention reAttention) {
		if(reAttention.isDelete()) {
			throw new ReAttentionNotFoundException("해당 조언 답글이 존재하지 않습니다.","reAttention");
		}
	}

	private void verfyIsMyReAttention(DeleteReAttention obj, ReAttention reAttention) {
		if (!reAttention.getWriter().equals(new Writer(obj.getDeleter()))) {
			throw new InvalidReAttentionException("자신의 조언 답글만 삭제할 수 있습니다.", "reAttention");
		}
	}
	
	public ReAttentionDeleteService(ReAttentionRepository reAttentionRepository) {
		this.reAttentionRepository = reAttentionRepository;
	}


}
