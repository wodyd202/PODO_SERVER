package com.ljy.podo.portfolio.service.loadPortfolio;

import java.time.LocalDate;

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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioSearchDTO {
	private String email;
	private LocalDate startDate;
	private LocalDate endDate;
	private int size;
	private int page;
	private PortfolioState state;
	private ShowType showType;

	public void setToday() {
		this.startDate = LocalDate.now();
		this.endDate = LocalDate.now();
		this.state = PortfolioState.CREATE;
	}
	
	public void setTemporary(User user) {
		this.email = user.getEmail().toString();
		this.state = PortfolioState.TEMPORARY;
	}
}
