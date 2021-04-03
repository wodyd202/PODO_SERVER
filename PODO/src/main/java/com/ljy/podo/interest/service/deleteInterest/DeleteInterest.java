package com.ljy.podo.interest.service.deleteInterest;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class DeleteInterest {
	private String portfolioId;
	
	@JsonIgnore
	private String deleter;
	
	public void setDeleter(User deleter) {
		this.deleter = deleter.getEmail().toString();
	}
}
