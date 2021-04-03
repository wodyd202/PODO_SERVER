package com.ljy.podo.portfolio.service.updatePortfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ljy.podo.portfolio.PortfolioState;
import com.ljy.podo.portfolio.ShowType;
import com.ljy.podo.user.aggregate.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdatePortfolio {
	private String id;
	private String title;
	private String header;
	private String content;
	private ShowType showType;
	private PortfolioState state;
	
	@JsonIgnore
	private String updater;
	
	public void setUpdater(User updater) {
		this.updater = updater.getEmail().toString();
	}
}
