package com.ljy.podo.portfolio.service.deletePortoflio;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class DeletePortfolio {
	private String id;
	
	@JsonIgnore
	private String deleter;
	
	public void setDeleter(User deleter) {
		this.deleter = deleter.getEmail().toString();
	}
}
