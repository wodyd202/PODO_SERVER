package com.ljy.podo.interest.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ljy.podo.config.security.oauth.LoginUser;
import com.ljy.podo.interest.infrastructure.SimpleInterestRepository;
import com.ljy.podo.interest.service.loadInterest.InterestSearchDTO;
import com.ljy.podo.interest.service.loadInterest.projection.InterestFullData;
import com.ljy.podo.portfolio.aggregate.exception.PortfolioNotFindException;
import com.ljy.podo.user.aggregate.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/interest")
@RequiredArgsConstructor
public class InterestLoadAPI {
	private final SimpleInterestRepository repository;

	@GetMapping
	public ResponseEntity<InterestFullData> find(InterestSearchDTO searchDTO, @LoginUser User user) {
		searchDTO.setInterester(user);
		verfyNotEmptyPortfolioId(searchDTO.getPortfolioId());
		InterestFullData findByPortfolioId = repository.findByPortfolioId(searchDTO);
		return new ResponseEntity<>(findByPortfolioId, HttpStatus.OK);
	}

	private void verfyNotEmptyPortfolioId(String portfolioId) {
		if (portfolioId == null || portfolioId.isEmpty()) {
			throw new PortfolioNotFindException("포트폴리오 고유번호를 입력해주세요.", "portfolio");
		}
	}
}
