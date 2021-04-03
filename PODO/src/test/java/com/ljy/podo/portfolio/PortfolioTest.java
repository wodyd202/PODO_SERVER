package com.ljy.podo.portfolio;

import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.service.registerPortfolio.RegisterPortfolio;

public interface PortfolioTest {
	default public Portfolio createMockPortfolio(String id,String writer) {
		return new Portfolio(new PortfolioId(id), 
				RegisterPortfolio.builder()
				.title("타이틀")
				.header("헤더")
				.content("내용")
				.showType(ShowType.PUBLIC)
				.state(PortfolioState.CREATE)
				.writer(writer).build());
	}
}
