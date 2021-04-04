package com.ljy.podo.portfolio.service.loadPortfolio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	private final int ONE_DAY = 86400000;
	private String email;
	private Date startDate;
	private Date endDate;
	private int size;
	private int page;
	private String major;
	private PortfolioState state;
	private ShowType showType;

	public void setMajor(User user) {
		this.major = user.getMajor().toString();
		this.showType = ShowType.PUBLIC;
		this.state = PortfolioState.CREATE; 
	}
	
	public void setToday() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.startDate = format.parse(format.format(new Date()));
			this.endDate = format.parse(format.format(new Date()));
			endDate.setTime(this.endDate.getTime() + ONE_DAY);
			this.endDate = new Date(this.endDate.getTime());
		} catch (ParseException e) {
		}
		this.state = PortfolioState.CREATE;
	}
	
	public void setTemporary(User user) {
		this.email = user.getEmail().toString();
		this.state = PortfolioState.TEMPORARY;
	}
}
