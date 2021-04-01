package com.ljy.podo.user.service.updateUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ljy.podo.user.Email;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpdateUser {
	
	@JsonIgnore
	private Email email;
	private String password;
	private String profile;
	
	public void setEmail(Email email) {
		this.email = email;
	}
}
