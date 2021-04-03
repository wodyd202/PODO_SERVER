package com.ljy.podo.interest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ljy.podo.interest.aggregate.exception.InvalidInterestException;
import com.ljy.podo.interest.infrastructure.InterestRepository;
import com.ljy.podo.interest.service.loadInterest.InterestSearchDTO;
import com.ljy.podo.interest.service.loadInterest.projection.InterestFullData;
import com.ljy.podo.interest.service.registerInterest.RegisterInterest;
import com.ljy.podo.interest.service.registerInterest.service.InterestRegisterService;
import com.ljy.podo.interest.service.util.InterestValidator;
import com.ljy.podo.portfolio.FakePortfolioRepository;
import com.ljy.podo.portfolio.PortfolioTest;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.aggregate.exception.PortfolioNotFindException;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;

public class InterestRegisterServiceTest implements PortfolioTest{
	
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
	}
	
	@Test
	@DisplayName("정상 케이스")
	void register_1() {
		RegisterInterest registerInterest = RegisterInterest
				.builder()
				.portfolioId("포트폴리오 아이디")
				.interester("wodyd202@naver.com")
				.build();
		
		interestRegisterService.register(registerInterest);
		
		InterestSearchDTO searchDTO = InterestSearchDTO.
				builder()
				.portfolioId("포트폴리오 아이디")
				.interester("wodyd202@naver.com")
				.build();
		
		InterestFullData interestData = interestRepository.findByPortfolioId(searchDTO);
		assertNotNull(interestData);
	}
	
	@Test
	@DisplayName("빈값을 입력했을 때 에러")
	void register_2() {
		assertThrows(PortfolioNotFindException.class, ()->{
			RegisterInterest registerInterest = RegisterInterest
					.builder()
					.portfolioId("")
					.interester("wodyd202@naver.com")
					.build();
			interestRegisterService.register(registerInterest);
		});
	}
	
	@Test
	@DisplayName("포트폴리오 정보가 존재하지 않을 때 에러")
	void register_3(){
		assertThrows(PortfolioNotFindException.class, ()->{
			RegisterInterest registerInterest = RegisterInterest
					.builder()
					.portfolioId("없음")
					.interester("wodyd202@naver.com")
					.build();
			interestRegisterService.register(registerInterest);
		});
	}
	
	@Test
	@DisplayName("중복 등록 에러")
	void register_4() {
		RegisterInterest registerInterest = RegisterInterest
				.builder()
				.portfolioId("포트폴리오 아이디")
				.interester("wodyd202@naver.com")
				.build();
		
		assertThrows(InvalidInterestException.class, ()->{
			interestRegisterService.register(registerInterest);
			interestRegisterService.register(registerInterest);
		});
	}
	
}
