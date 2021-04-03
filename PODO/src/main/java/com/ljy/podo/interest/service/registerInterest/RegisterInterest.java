package com.ljy.podo.interest.service.registerInterest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ljy.podo.interest.InterestId;
import com.ljy.podo.interest.aggregate.Interest;
import com.ljy.podo.user.aggregate.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterInterest {
	private String portfolioId;
	
	@JsonIgnore
	private String interester;

	public void setInterester(User interester) {
		this.interester = interester.getEmail().toString();
	}
	
	public Interest toEntity(InterestId interestId) {
		return new Interest(interestId, this);
	}
}
