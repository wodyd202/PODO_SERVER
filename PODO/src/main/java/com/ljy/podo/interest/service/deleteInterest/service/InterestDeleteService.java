package com.ljy.podo.interest.service.deleteInterest.service;

import org.springframework.transaction.annotation.Transactional;

import com.ljy.podo.interest.aggregate.exception.InvalidInterestException;
import com.ljy.podo.interest.infrastructure.InterestRepository;
import com.ljy.podo.interest.service.deleteInterest.DeleteInterest;
import com.ljy.podo.interest.service.loadInterest.InterestSearchDTO;
import com.ljy.podo.interest.service.loadInterest.projection.InterestFullData;

public class InterestDeleteService {
	private InterestRepository interestRepository;
	
	@Transactional
	public void delete(DeleteInterest deleteInterest) {
		InterestFullData findByPortfolioId = interestRepository.findByPortfolioId(InterestSearchDTO.builder()
				.interester(deleteInterest.getDeleter())
				.portfolioId(deleteInterest.getPortfolioId()).build());
		verfyNotNullInterest(findByPortfolioId);
		interestRepository.remove(findByPortfolioId);
	}

	private void verfyNotNullInterest(InterestFullData findByPortfolioId) {
		if (findByPortfolioId == null) {
			throw new InvalidInterestException("포트폴리오에 대한 자신의 관심도가 존재하지 않습니다.", "interest");
		}
	}

	public InterestDeleteService(InterestRepository interestRepository) {
		this.interestRepository = interestRepository;
	}

}
