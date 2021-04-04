package com.ljy.podo.reattention;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ljy.podo.reAttention.ReAttentionId;
import com.ljy.podo.reAttention.ReAttentionState;
import com.ljy.podo.reAttention.aggregate.ReAttention;
import com.ljy.podo.reAttention.infrastructure.ReAttentionRepository;
import com.ljy.podo.reAttention.service.loadReAttention.ReAttentionSearchDTO;
import com.ljy.podo.reAttention.service.loadReAttention.projection.ReAttentionListData;

public class FakeReAttentionRepository implements ReAttentionRepository {
	private final HashMap<String, List<ReAttention>> repository = new HashMap<>();

	@Override
	public void save(ReAttention entity) {
		String attentionId = entity.getAttentionId().toString();
		List<ReAttention> list = repository.get(attentionId);
		if (list == null) {
			list = new ArrayList<>();
			repository.put(attentionId, list);
		}
		repository.get(attentionId).add(entity);
	}

	@Override
	public long countAll(ReAttentionSearchDTO searchDTO) {
		String attentionId = searchDTO.getAttentionId();
		return repository.get(attentionId) == null ? 0 : repository.get(attentionId).size();
	}

	@Override
	public Optional<ReAttention> findById(ReAttentionId reAttentionId) {
		Set<String> keySet = repository.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			List<ReAttention> list = repository.get(iterator.next());
			for (int i = 0; i < list.size(); i++) {
				ReAttention reAttention = list.get(i);
				if(reAttention.getReAttentionId().equals(reAttentionId) && reAttention.getState() != ReAttentionState.DELETE) {
					return Optional.ofNullable(reAttention);
				}
			}
		}
		return Optional.ofNullable(null);
	}

	@Override
	public List<ReAttentionListData> findAll(ReAttentionSearchDTO searchDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
