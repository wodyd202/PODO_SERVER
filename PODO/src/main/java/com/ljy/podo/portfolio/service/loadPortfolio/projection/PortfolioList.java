package com.ljy.podo.portfolio.service.loadPortfolio.projection;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PortfolioList {
	private List<PortfolioListData> content;
	private long totalElement;
}
