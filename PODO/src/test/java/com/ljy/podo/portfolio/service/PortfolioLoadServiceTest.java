package com.ljy.podo.portfolio.service;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.PortfolioState;
import com.ljy.podo.portfolio.ShowType;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;
import com.ljy.podo.portfolio.service.loadPortfolio.PortfolioSearchDTO;
import com.ljy.podo.portfolio.service.loadPortfolio.service.PortfolioLoadService;
import com.ljy.podo.portfolio.service.registerPortfolio.RegisterPortfolio;
import com.ljy.podo.user.Major;

@SpringBootTest
public class PortfolioLoadServiceTest {
	
	@Autowired	
	PortfolioLoadService portfolioLoadService;

	@Autowired
	private PortfolioRepository portfolioRepository;
	
	@Test
	void load_1() {
		portfolioRepository.save(new Portfolio(new PortfolioId(UUID.randomUUID().toString()), RegisterPortfolio.builder()
				.title("타이틀")
				.header("헤더")
				.content("내용")
				.showType(ShowType.PUBLIC)
				.state(PortfolioState.CREATE)
				.writer("wodyd202@naver.com")
				.major(new Major("컴퓨터"))
				.build()));
		portfolioRepository.save(new Portfolio(new PortfolioId(UUID.randomUUID().toString()), RegisterPortfolio.builder()
				.title("타이틀")
				.header("헤더")
				.content("내용")
				.showType(ShowType.PUBLIC)
				.state(PortfolioState.CREATE)
				.writer("wodyd202@naver.com")
				.major(new Major("컴퓨터"))
				.build()));
		portfolioRepository.save(new Portfolio(new PortfolioId(UUID.randomUUID().toString()), RegisterPortfolio.builder()
				.title("타이틀")
				.header("헤더")
				.content("내용")
				.showType(ShowType.PUBLIC)
				.state(PortfolioState.CREATE)
				.writer("wodyd202@naver.com")
				.major(new Major("컴퓨터"))
				.build()));
		System.out.println("===============================================================================");
		PortfolioSearchDTO searchDTO = PortfolioSearchDTO
				.builder()
				.page(0)
				.size(9)
				.email("wodyd202@naver.com")
				.startDate(LocalDate.now())
				.endDate(LocalDate.now())
				.build();
		portfolioLoadService.findAll(searchDTO);
		System.out.println("===============================================================================");
	}
}
