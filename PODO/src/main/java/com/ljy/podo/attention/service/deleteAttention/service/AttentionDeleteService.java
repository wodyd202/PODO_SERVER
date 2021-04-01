package com.ljy.podo.attention.service.deleteAttention.service;

import org.springframework.transaction.annotation.Transactional;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.attention.aggregate.Attention;
import com.ljy.podo.attention.aggregate.exception.AttentionNotFoundException;
import com.ljy.podo.attention.aggregate.exception.InvalidAttentionException;
import com.ljy.podo.attention.infrastructure.AttentionRepository;
import com.ljy.podo.attention.service.deleteAttention.DeleteAttention;
import com.ljy.podo.portfolio.Writer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttentionDeleteService {
	private final AttentionRepository attentionRepository;

	@Transactional
	public void delete(DeleteAttention deleteAttention) {
		Attention attention = attentionRepository.findById(new AttentionId(deleteAttention.getAttentionId()))
				.orElseThrow(() -> new AttentionNotFoundException("해당 조언이 존재하지 않습니다. 다시 확인해주세요", "attention"));
		verfyIsMyAttention(deleteAttention, attention);
		verfyAleadyDeleteAttention(attention);
		attention.delete();
	}

	private void verfyAleadyDeleteAttention(Attention attention) {
		if(attention.isDelete()) {
			throw new AttentionNotFoundException("존재하지 않는 조언입니다. 다시 확인해주세요.", "attention");
		}
	}
	
	private void verfyIsMyAttention(DeleteAttention obj, Attention attention) {
		if (!attention.getWriter().equals(new Writer(obj.getDeleter()))) {
			throw new InvalidAttentionException("자신의 조언만 삭제할 수 있습니다.", "attention");
		}
	}
}
