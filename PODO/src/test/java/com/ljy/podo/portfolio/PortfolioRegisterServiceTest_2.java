package com.ljy.podo.portfolio;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.aggregate.exception.InvalidPortfolioException;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;
import com.ljy.podo.portfolio.service.updatePortfolio.UpdatePortfolio;
import com.ljy.podo.portfolio.service.updatePortfolio.service.PortfolioUpdateService;
import com.ljy.podo.portfolio.service.util.UpdatePortfolioValidator;

public class PortfolioRegisterServiceTest_2 implements PortfolioTest{
	
	PortfolioRepository fakePortfolioRepository;
	PortfolioUpdateService portfolioUpdateService;
	UpdatePortfolioValidator updatePortfolioValidator;
	
	@BeforeEach
	void setUp() {
		updatePortfolioValidator = new UpdatePortfolioValidator();
		fakePortfolioRepository = new FakePortfolioRepository();
		portfolioUpdateService = new PortfolioUpdateService(updatePortfolioValidator, fakePortfolioRepository);
	}
	
	@Test
	@DisplayName("정상 등록 케이스")
	public void register_1() {
		assertThrows(InvalidPortfolioException.class, ()->{
			Portfolio createMockPortfolio = createMockPortfolio("아이디");
			fakePortfolioRepository.save(createMockPortfolio);
			UpdatePortfolio updatePortfolio = UpdatePortfolio.builder()
					.id("아이디")
					.title("타이틀")
					.header("헤더")
					.content("내용")
					.updater("wodyd202@naver.com")
					.showType(ShowType.PUBLIC)
					.state(PortfolioState.TEMPORARY)
					.build();
			portfolioUpdateService.register(updatePortfolio);
		});
	}
}
