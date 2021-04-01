package com.ljy.podo.portfolio;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.aggregate.exception.InvalidPortfolioException;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;
import com.ljy.podo.portfolio.service.deletePortoflio.DeletePortfolio;
import com.ljy.podo.portfolio.service.deletePortoflio.service.PortfolioDeleteService;

public class PortfolioDeleteServiceTest implements PortfolioTest{
	
	PortfolioRepository portfolioRepository;
	
	@BeforeEach
	void setUp() {
		portfolioRepository = new FakePortfolioRepository();
		Portfolio createMockPortfolio = createMockPortfolio("아이디");
		portfolioRepository.save(createMockPortfolio);
	}
	
	@Test
	@DisplayName("정상 삭제 케이스")
	void delete_1() {
		DeletePortfolio deletePortfolio = DeletePortfolio
				.builder()
				.id("아이디")
				.deleter("wodyd202@naver.com")
				.build();
		PortfolioDeleteService portfolioDeleteService = new PortfolioDeleteService(portfolioRepository);
		portfolioDeleteService.delete(deletePortfolio);
		Optional<Portfolio> findById = portfolioRepository.findById(new PortfolioId("아이디"));
		if(findById.isPresent()) {
			if(findById.get().getState() != PortfolioState.DELETE) {
				fail();
			}
		}
	}
	
	@Test
	@DisplayName("자신의 포트폴리오를 삭제하는 것이 아닐때 에러")
	public void delete_2() {
		assertThrows(InvalidPortfolioException.class, ()->{
			DeletePortfolio deletePortfolio = DeletePortfolio
					.builder()
					.id("아이디")
					.deleter("test@naver.com")
					.build();
			PortfolioDeleteService portfolioDeleteService = new PortfolioDeleteService(portfolioRepository);
			portfolioDeleteService.delete(deletePortfolio);
		});
	}
}
