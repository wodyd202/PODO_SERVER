package com.ljy.podo.portfolio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.aggregate.exception.InvalidPortfolioException;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;
import com.ljy.podo.portfolio.service.registerPortfolio.RegisterPortfolio;
import com.ljy.podo.portfolio.service.updatePortfolio.UpdatePortfolio;
import com.ljy.podo.portfolio.service.updatePortfolio.service.PortfolioUpdateService;
import com.ljy.podo.portfolio.service.util.UpdatePortfolioValidator;

public class PortfolioUpdateServiceTest {
	
	PortfolioUpdateService portfolioUpdateService;
	UpdatePortfolioValidator portfolioUpdateValidator;
	PortfolioRepository fakePortfolioRepository;
	
	@BeforeEach
	void setUp() {
		portfolioUpdateValidator = new UpdatePortfolioValidator();
		fakePortfolioRepository = new FakePortfolioRepository();
		portfolioUpdateService = new PortfolioUpdateService(portfolioUpdateValidator,fakePortfolioRepository);
		fakePortfolioRepository.save(createMockPortfolio("아이디"));
	}
	
	@Test
	@DisplayName("정상 케이스")
	public void update_1() {
		UpdatePortfolio updatePortfolio = UpdatePortfolio
				.builder()
				.id("아이디")
				.title("수정 타이틀")
				.header("수정 헤더")
				.content("<img src=\"testImage\">")
				.updater("wodyd202@naver.com")
				.showType(ShowType.PUBLIC)
				.state(PortfolioState.CREATE)
				.build();
		
		portfolioUpdateService.register(updatePortfolio);
		
		Optional<Portfolio> result = fakePortfolioRepository.findById(new PortfolioId("아이디"));
		if(!result.isPresent()) {
			fail();
		}
		Portfolio portfolio = result.get();
		
		assertThat(portfolio.getTitle().toString()).isEqualTo("수정 타이틀");
		assertThat(portfolio.getHeader().toString()).isEqualTo("수정 헤더");
		assertThat(portfolio.getDetail().getContent().toString()).isEqualTo("<img src=\"testImage\">");
		assertThat(portfolio.getDetail().getImages().toString()).isEqualTo("testImage");
	}
	
	@Test
	@DisplayName("빈값이 존재할때 에러")
	public void update_2() {
		assertThrows(InvalidPortfolioException.class, ()->{
			UpdatePortfolio updatePortfolio = UpdatePortfolio
					.builder()
					.id("")
					.title("수정 타이틀")
					.header("수정 헤더")
					.content("수정 콘텐츠")
					.updater("wodyd202@naver.com")
					.showType(ShowType.PUBLIC)
					.state(PortfolioState.CREATE)
					.build();
			portfolioUpdateService.register(updatePortfolio);
		});
		assertThrows(InvalidPortfolioException.class, ()->{
			UpdatePortfolio updatePortfolio = UpdatePortfolio
					.builder()
					.id("아이디")
					.title("")
					.header("수정 헤더")
					.content("수정 콘텐츠")
					.updater("wodyd202@naver.com")
					.showType(ShowType.PUBLIC)
					.state(PortfolioState.CREATE)
					.build();
			portfolioUpdateService.register(updatePortfolio);
		});
		assertThrows(InvalidPortfolioException.class, ()->{
			UpdatePortfolio updatePortfolio = UpdatePortfolio
					.builder()
					.id("아이디")
					.title("타이틀")
					.header("")
					.content("수정 콘텐츠")
					.updater("wodyd202@naver.com")
					.showType(ShowType.PUBLIC)
					.state(PortfolioState.CREATE)
					.build();
			portfolioUpdateService.register(updatePortfolio);
		});
		assertThrows(InvalidPortfolioException.class, ()->{
			UpdatePortfolio updatePortfolio = UpdatePortfolio
					.builder()
					.id("아이디")
					.title("타이틀")
					.header("헤더")
					.content("")
					.updater("wodyd202@naver.com")
					.showType(ShowType.PUBLIC)
					.state(PortfolioState.CREATE)
					.build();
			portfolioUpdateService.register(updatePortfolio);
		});
	}
	
	@Test
	@DisplayName("자신의 포트폴리오가 아닐때 에러")
	public void update_3() {
		assertThrows(InvalidPortfolioException.class, ()->{
			UpdatePortfolio updatePortfolio = UpdatePortfolio
					.builder()
					.id("아이디")
					.title("타이틀")
					.header("헤더")
					.content("내용")
					.updater("error@naver.com")
					.showType(ShowType.PUBLIC)
					.state(PortfolioState.CREATE)
					.build();
			portfolioUpdateService.register(updatePortfolio);
		});
	}
	
	private Portfolio createMockPortfolio(String id) {
		return new Portfolio(new PortfolioId(id), 
				RegisterPortfolio.builder()
				.title("타이틀")
				.header("헤더")
				.content("내용")
				.showType(ShowType.PUBLIC)
				.state(PortfolioState.CREATE)
				.writer("wodyd202@naver.com").build());
	}
}
