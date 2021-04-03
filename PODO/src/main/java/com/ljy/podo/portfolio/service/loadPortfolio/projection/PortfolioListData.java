package com.ljy.podo.portfolio.service.loadPortfolio.projection;

import java.util.Date;

import com.ljy.podo.portfolio.PortfolioState;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PortfolioListData {
	private String id;
	private String title;
	private String writer;
	private String major;
	private String header;
	private String content;
	private Date createDate;
	private String images;
	private PortfolioState state;
	private long attentionCnt;
	private long interestCnt;
}
