package com.ljy.podo.attention.infrastructure;

import java.util.List;
import java.util.Optional;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.attention.aggregate.Attention;
import com.ljy.podo.attention.service.loadAttention.AttentionSearchDTO;
import com.ljy.podo.attention.service.loadAttention.projection.AttentionListData;

public interface AttentionRepository {
	void save(Attention attention);

	long countAll(AttentionSearchDTO search);

	Optional<Attention> findById(AttentionId attentionId);

	List<AttentionListData> findAll(AttentionSearchDTO search);
}
