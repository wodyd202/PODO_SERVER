package com.ljy.podo.reAttention.infrastructure;

import java.util.List;
import java.util.Optional;

import com.ljy.podo.reAttention.ReAttentionId;
import com.ljy.podo.reAttention.aggregate.ReAttention;
import com.ljy.podo.reAttention.service.loadReAttention.ReAttentionSearchDTO;
import com.ljy.podo.reAttention.service.loadReAttention.projection.ReAttentionListData;

public interface ReAttentionRepository {
	void save(ReAttention entity);

	long countAll(ReAttentionSearchDTO searchDTO);

	List<ReAttentionListData> findAll(ReAttentionSearchDTO searchDTO);

	Optional<ReAttention> findById(ReAttentionId reAttentionId);
}
