package com.ljy.podo.user;

import com.ljy.podo.user.aggregate.User.UserType;
import com.ljy.podo.user.service.registerUser.RegisterUser;
import com.ljy.podo.user.service.updateUser.UpdateUser;

public class UserTest {
	protected RegisterUser createUser(String email,String password,String major) {
		return RegisterUser.builder()
				.email(email)
				.password(password)
				.major(major)
				.userType(UserType.STUDENT)
				.build();
	}

	protected UpdateUser updateUser(String email,String password,String profile) {
		return UpdateUser.builder()
				.email(new Email(email))
				.password(password)
				.profile(profile)
				.build();
	}
}
