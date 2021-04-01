package com.ljy.podo.portfolio.service.loadPortfolio.projection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PortfolioDetailData {
	private String content;
	private String lastModify;
}
