package com.ljy.podo.user.service.registerUser;

import com.ljy.podo.user.Email;
import com.ljy.podo.user.Major;
import com.ljy.podo.user.Password;
import com.ljy.podo.user.aggregate.User;
import com.ljy.podo.user.aggregate.User.UserType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterUser {
	private String email;
	private String password;
	private String major;
	private UserType userType;
	
	public User toEntity() {
		return new User(
				new Email(email),
				new Password(password),
				new Major(major),
				userType);
	}
}
