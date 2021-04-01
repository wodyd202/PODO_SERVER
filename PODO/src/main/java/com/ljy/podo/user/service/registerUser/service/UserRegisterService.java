package com.ljy.podo.user.service.registerUser.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ljy.podo.common.RegisterService;
import com.ljy.podo.common.Validator;
import com.ljy.podo.major.infrastructure.MajorRepository;
import com.ljy.podo.user.Email;
import com.ljy.podo.user.Major;
import com.ljy.podo.user.aggregate.User;
import com.ljy.podo.user.aggregate.exception.DupUserException;
import com.ljy.podo.user.aggregate.exception.InvalidUserException;
import com.ljy.podo.user.infrastructure.UserRepository;
import com.ljy.podo.user.service.registerUser.RegisterUser;

public class UserRegisterService extends RegisterService<RegisterUser> {

	private final UserRepository userRepository;
	private final MajorRepository majorRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	protected void afterValidation(RegisterUser obj) {
		verfyExistMajor(obj);
		Optional<User> findUser = userRepository.findByEmail(new Email(obj.getEmail()));
		if (findUser.isPresent()) {
			throw new DupUserException("중복으로 가입된 이메일이 존재합니다. 다른 이메일을 입력해주세요.", "email");
		}
	}

	@Override
	protected void reigsterEntity(RegisterUser obj) {
		User entity = obj.toEntity();
		entity.encodePassword(passwordEncoder);
		userRepository.save(entity);
	}

	private void verfyExistMajor(RegisterUser obj) {
		if (!majorRepository.existByName(new Major(obj.getMajor()))) {
			throw new InvalidUserException("전공 정보가 존재하지 않습니다. 다른 전공을 입력해주세요.", "major");
		}
	}

	public UserRegisterService(Validator<RegisterUser> validate, UserRepository userRepository,
			MajorRepository majorRepository, PasswordEncoder passwordEncoder) {
		super(validate);
		this.userRepository = userRepository;
		this.majorRepository = majorRepository;
		this.passwordEncoder = passwordEncoder;
	}
}
