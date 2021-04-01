package com.ljy.podo.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ljy.podo.config.security.oauth.LoginUser;
import com.ljy.podo.user.aggregate.User;
import com.ljy.podo.user.service.registerUser.RegisterUser;
import com.ljy.podo.user.service.registerUser.service.UserRegisterService;
import com.ljy.podo.user.service.updateUser.UpdateUser;
import com.ljy.podo.user.service.updateUser.service.UserUpdateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserUpdateAPI {
	private final UserRegisterService userRegisterService;
	private final UserUpdateService userUpdateService;

	@PostMapping
	public ResponseEntity<RegisterUser> registerUser(@RequestBody RegisterUser registerUser) {
		userRegisterService.register(registerUser);
		return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser,
			@LoginUser User user) {
		updateUser.setEmail(user.getEmail());
		userUpdateService.register(updateUser);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}

}
