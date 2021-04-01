package com.ljy.podo.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ljy.podo.major.infrastructure.SimpleMajorRepository;
import com.ljy.podo.user.infrastructure.SimpleUserRepository;
import com.ljy.podo.user.service.registerUser.service.UserRegisterService;
import com.ljy.podo.user.service.updateUser.service.UserUpdateService;
import com.ljy.podo.user.service.util.RegisterUserValiator;
import com.ljy.podo.user.service.util.UpdateUserValiator;

@Configuration
public class UserConfig {

	@Autowired
	private SimpleMajorRepository majorRepository;

	@Autowired
	private SimpleUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public RegisterUserValiator registerUserValiator() {
		return new RegisterUserValiator();
	}

	@Bean
	public UpdateUserValiator updateUserValiator() {
		return new UpdateUserValiator();
	}

	@Bean
	public UserRegisterService userRegisterService() {
		return new UserRegisterService(registerUserValiator(), userRepository, majorRepository, passwordEncoder);
	}

	@Bean
	public UserUpdateService userUpdateService() {
		return new UserUpdateService(updateUserValiator(), userRepository, passwordEncoder);
	}
}
