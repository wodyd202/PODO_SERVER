package com.ljy.podo.interest.service.loadInterest;

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
public class InterestSearchDTO {
	private String portfolioId;
	private String interester;
	
	public void setInterester(User interester) {
		this.interester = interester.getEmail().toString();
	}
}
