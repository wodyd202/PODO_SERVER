package com.ljy.podo.portfolio.service.registerPortfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.PortfolioState;
import com.ljy.podo.portfolio.ShowType;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.user.Major;
import com.ljy.podo.user.aggregate.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPortfolio {
	private String title;
	private String header;
	private String content;
	private ShowType showType;
	private PortfolioState state;
	
	@JsonIgnore
	private String writer;

	@JsonIgnore
	private Major major;

	public void setMajorAndWriter(User user) {
		this.major = user.getMajor();
		this.writer = user.getEmail().toString();
	}

	public Portfolio toEntity(PortfolioId portfolioId) {
		return new Portfolio(portfolioId, this);
	}
	
}
