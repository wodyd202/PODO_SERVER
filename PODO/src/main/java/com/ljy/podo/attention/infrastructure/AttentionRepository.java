package com.ljy.podo.attention.infrastructure;

import java.util.Optional;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.attention.aggregate.Attention;
import com.ljy.podo.attention.service.loadAttention.AttentionSearchDTO;

public interface AttentionRepository {
	void save(Attention attention);

	long countAll(AttentionSearchDTO search);

	Optional<Attention> findById(AttentionId attentionId);
}
