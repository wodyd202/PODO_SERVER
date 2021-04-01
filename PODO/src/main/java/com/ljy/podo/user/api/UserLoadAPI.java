package com.ljy.podo.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ljy.podo.user.service.loadUser.service.UserLoadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserLoadAPI {

	private final UserLoadService userLoadService;

	@GetMapping("is-dup")
	public ResponseEntity<Void> dupUser(@RequestParam(required = true, defaultValue = "") String email) {
		try {
			userLoadService.loadUserByUsername(email);
		} catch (UsernameNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
