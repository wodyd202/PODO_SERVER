package com.ljy.podo.portfolio;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ljy.podo.portfolio.aggregate.exception.InvalidPortfolioException;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;
import com.ljy.podo.portfolio.service.registerPortfolio.RegisterPortfolio;
import com.ljy.podo.portfolio.service.registerPortfolio.service.PortfolioRegisterService;
import com.ljy.podo.portfolio.service.util.RegisterPortfolioValidator;

public class PortfolioRegisterServiceTest_1 implements PortfolioTest{
	RegisterPortfolioValidator registerPortfolioValitator;
	PortfolioRepository fakePortfolioRepository;
	PortfolioRegisterService portfolioRegisterService;

	@BeforeEach
	void setUp() {
		registerPortfolioValitator = new RegisterPortfolioValidator();
		fakePortfolioRepository = new FakePortfolioRepository();
		portfolioRegisterService = new PortfolioRegisterService(registerPortfolioValitator,fakePortfolioRepository);
	}
	
	@Test
	@DisplayName("정상 등록 케이스")
	public void register_1() {
		RegisterPortfolio registerPortfolio = RegisterPortfolio.builder()
				.title("타이틀")
				.header("헤더")
				.content("내용")
				.showType(ShowType.PUBLIC)
				.state(PortfolioState.CREATE)
				.writer("wodyd202@naver.com")
				.build();
		
		portfolioRegisterService.register(registerPortfolio);
	}
	
	@Test
	@DisplayName("빈값이 있는 경우 에러")
	public void register_2() {
		assertThrows(InvalidPortfolioException.class, ()->{
			RegisterPortfolio registerPortfolio = RegisterPortfolio.builder()
					.title("")
					.header("헤더")
					.content("내용")
					.showType(ShowType.PUBLIC)
					.state(PortfolioState.CREATE)
					.writer("wodyd202@naver.com")
					.build();
			portfolioRegisterService.register(registerPortfolio);
		});
		assertThrows(InvalidPortfolioException.class, ()->{
			RegisterPortfolio registerPortfolio = RegisterPortfolio.builder()
					.title("타이틀")
					.header("")
					.content("내용")
					.showType(ShowType.PUBLIC)
					.state(PortfolioState.CREATE)
					.writer("wodyd202@naver.com")
					.build();
			portfolioRegisterService.register(registerPortfolio);
		});
		assertThrows(InvalidPortfolioException.class, ()->{
			RegisterPortfolio registerPortfolio = RegisterPortfolio.builder()
					.title("타이틀")
					.header("헤더")
					.content("")
					.showType(ShowType.PUBLIC)
					.state(PortfolioState.CREATE)
					.writer("wodyd202@naver.com")
					.build();
			portfolioRegisterService.register(registerPortfolio);
		});
	}
}
