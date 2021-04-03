package com.ljy.podo.interest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ljy.podo.interest.aggregate.Interest;
import com.ljy.podo.interest.infrastructure.InterestRepository;
import com.ljy.podo.interest.service.loadInterest.InterestSearchDTO;
import com.ljy.podo.interest.service.loadInterest.projection.InterestFullData;
import com.ljy.podo.portfolio.PortfolioId;

public class FakeInterestRepository implements InterestRepository {

	private final Map<String, List<Interest>> repository = new HashMap<>();

	@Override
	public InterestFullData findByPortfolioId(InterestSearchDTO searchDTO) {
		List<Interest> interests = repository.get(searchDTO.getPortfolioId());
		if(interests == null) {
			interests = Collections.emptyList();
		}
		for (int i = 0; i < interests.size(); i++) {
			Interest interest = interests.get(i);
			if (interest.getInterester().equals(new Interester(searchDTO.getInterester()))
					&& interest.getPortfolioId().equals(new PortfolioId(searchDTO.getPortfolioId()))) {
				return new InterestFullData(interest.getPortfolioId().toString(), interest.getInterester().toString(),
						interest.getCreateDate());
			}
		}
		return null;
	}

	@Override
	public void save(Interest entity) {
		List<Interest> interests = repository.get(entity.getPortfolioId().toString());
		boolean isEmptyPortfolio = interests == null;
		if (isEmptyPortfolio) {
			repository.put(entity.getPortfolioId().toString(), new ArrayList<>());
		}
		interests = repository.get(entity.getPortfolioId().toString());
		interests.add(entity);
	}

	@Override
	public void remove(InterestFullData findByPortfolioId) {
		List<Interest> interests = repository.get(findByPortfolioId.getPortfolioId().toString());
		for(int i =0;i<interests.size();i++) {
			Interest interest = interests.get(i);
			if (interest.getInterester().equals(new Interester(findByPortfolioId.getInterester()))
					&& interest.getPortfolioId().equals(new PortfolioId(findByPortfolioId.getPortfolioId()))) {
				interests.remove(i);
				return;
			}
		}
	}

}
