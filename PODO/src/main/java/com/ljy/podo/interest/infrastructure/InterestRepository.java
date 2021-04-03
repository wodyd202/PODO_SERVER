package com.ljy.podo.interest.infrastructure;

import com.ljy.podo.interest.aggregate.Interest;
import com.ljy.podo.interest.service.loadInterest.InterestSearchDTO;
import com.ljy.podo.interest.service.loadInterest.projection.InterestFullData;

public interface InterestRepository {
	InterestFullData findByPortfolioId(InterestSearchDTO searchDTO);

	void save(Interest entity);

	void remove(InterestFullData findByPortfolioId);
}
