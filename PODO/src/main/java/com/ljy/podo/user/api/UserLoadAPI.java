package com.ljy.podo.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ljy.podo.user.aggregate.exception.InvalidUserException;
import com.ljy.podo.user.service.loadUser.service.UserLoadService;
import com.ljy.podo.user.service.util.RegisterUserValiator;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserLoadAPI {
	private final RegisterUserValiator registerUserValiator;
	private final UserLoadService userLoadService;

	@GetMapping("count")
	public ResponseEntity<Long> countAll(){
		return new ResponseEntity<>(userLoadService.countAll(), HttpStatus.OK);
	}
	
	@GetMapping("is-dup")
	public ResponseEntity<Void> dupUser(@RequestParam(required = true, defaultValue = "") String email) {
		boolean isEmail = registerUserValiator.isValidEmail(email);
		if (!isEmail) {
			throw new InvalidUserException("이메일 형식이 올바르지 않습니다. 다시 입력해주세요.", "email");
		}
		try {
			userLoadService.loadUserByUsername(email);
		} catch (UsernameNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
