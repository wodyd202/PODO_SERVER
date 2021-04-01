package com.ljy.podo.user.service.updateUser.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ljy.podo.common.RegisterService;
import com.ljy.podo.common.Validator;
import com.ljy.podo.user.aggregate.User;
import com.ljy.podo.user.infrastructure.UserRepository;
import com.ljy.podo.user.service.updateUser.UpdateUser;

public class UserUpdateService extends RegisterService<UpdateUser> {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	protected void reigsterEntity(UpdateUser obj) {
		User findUser = userRepository.findByEmail(obj.getEmail()).get();
		findUser.update(obj);
		findUser.encodePassword(passwordEncoder);
	}

	public UserUpdateService(Validator<UpdateUser> validate, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		super(validate);
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
}
