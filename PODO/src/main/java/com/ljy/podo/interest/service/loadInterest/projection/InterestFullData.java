package com.ljy.podo.interest.service.loadInterest.projection;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestFullData {
	private String portfolioId;
	private String interester;
	private Date createDate;
}
