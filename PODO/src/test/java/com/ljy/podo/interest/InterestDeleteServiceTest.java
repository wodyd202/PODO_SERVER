package com.ljy.podo.interest;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ljy.podo.interest.infrastructure.InterestRepository;
import com.ljy.podo.interest.service.deleteInterest.DeleteInterest;
import com.ljy.podo.interest.service.deleteInterest.service.InterestDeleteService;
import com.ljy.podo.interest.service.loadInterest.InterestSearchDTO;
import com.ljy.podo.interest.service.loadInterest.projection.InterestFullData;
import com.ljy.podo.interest.service.registerInterest.RegisterInterest;
import com.ljy.podo.interest.service.registerInterest.service.InterestRegisterService;
import com.ljy.podo.interest.service.util.InterestValidator;
import com.ljy.podo.portfolio.FakePortfolioRepository;
import com.ljy.podo.portfolio.PortfolioTest;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;

public class InterestDeleteServiceTest implements PortfolioTest{

	PortfolioRepository portfolioRepository;
	InterestValidator interestValidator;
	InterestRepository interestRepository;
	InterestRegisterService interestRegisterService;
	
	@BeforeEach
	void setUp() {
		portfolioRepository = new FakePortfolioRepository();
		interestValidator = new InterestValidator();
		interestRepository = new FakeInterestRepository();
		
		interestRegisterService = new InterestRegisterService(interestValidator,portfolioRepository,interestRepository);
		
		Portfolio createMockPortfolio = createMockPortfolio("포트폴리오 아이디","test@naver.com");
		portfolioRepository.save(createMockPortfolio);
		
		interestRepository.save(RegisterInterest
				.builder()
				.portfolioId("포트폴리오 아이디")
				.interester("wodyd202@naver.com")
				.build().toEntity(new InterestId("관심도 아이디")));
	}
	
	@Test
	@DisplayName("정상 케이스")
	void delete_1() {
		DeleteInterest deleteInterest = DeleteInterest
				.builder()
				.portfolioId("포트폴리오 아이디")
				.deleter("wodyd202@naver.com")
				.build();
		
		InterestDeleteService interestDeleteService = new InterestDeleteService(interestRepository);
		interestDeleteService.delete(deleteInterest);
		
		InterestFullData findInterest = interestRepository.findByPortfolioId(InterestSearchDTO.
				builder()
				.portfolioId("포트폴리오 아이디")
				.interester("wodyd202@naver.com")
				.build());
		assertNull(findInterest);
	}
	
	@Test
	@DisplayName("관심 삭제 후 재 등록")
	void delete_2() {
		DeleteInterest deleteInterest = DeleteInterest
				.builder()
				.portfolioId("포트폴리오 아이디")
				.deleter("wodyd202@naver.com")
				.build();
		
		InterestDeleteService interestDeleteService = new InterestDeleteService(interestRepository);
		interestDeleteService.delete(deleteInterest);
		
		RegisterInterest registerInterest = RegisterInterest
				.builder()
				.portfolioId("포트폴리오 아이디")
				.interester("wodyd202@naver.com")
				.build();
		
		interestRegisterService.register(registerInterest);
	}
}
