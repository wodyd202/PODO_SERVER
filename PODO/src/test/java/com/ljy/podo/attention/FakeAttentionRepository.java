package com.ljy.podo.attention;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ljy.podo.attention.aggregate.Attention;
import com.ljy.podo.attention.infrastructure.AttentionRepository;
import com.ljy.podo.attention.service.loadAttention.AttentionSearchDTO;
import com.ljy.podo.attention.service.loadAttention.projection.AttentionListData;

public class FakeAttentionRepository implements AttentionRepository {

	private final List<Attention> repository = new ArrayList<>();

	@Override
	public void save(Attention attention) {
		repository.add(attention);
	}

	@Override
	public long countAll(AttentionSearchDTO search) {
		int count = 0;
		for (int i = 0; i < repository.size(); i++) {
			if (search.getPortfolioId() != null) {
				Attention attention = repository.get(i);
				if (attention.getPortfolioId().toString().equals(search.getPortfolioId())) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public Optional<Attention> findById(AttentionId attentionId) {
		for (int i = 0; i < repository.size(); i++) {
			if(repository.get(i).getAttentionId().equals(attentionId)) {
				return Optional.ofNullable(repository.get(i));
			}
		}
		return Optional.ofNullable(null);
	}

	@Override
	public List<AttentionListData> findAll(AttentionSearchDTO search) {
		return null;
	}

}
