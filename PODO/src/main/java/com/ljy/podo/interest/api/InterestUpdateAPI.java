package com.ljy.podo.interest.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ljy.podo.config.security.oauth.LoginUser;
import com.ljy.podo.interest.service.deleteInterest.DeleteInterest;
import com.ljy.podo.interest.service.deleteInterest.service.InterestDeleteService;
import com.ljy.podo.interest.service.registerInterest.RegisterInterest;
import com.ljy.podo.interest.service.registerInterest.service.InterestRegisterService;
import com.ljy.podo.user.aggregate.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/interest")
@RequiredArgsConstructor
public class InterestUpdateAPI {
	
	private final InterestRegisterService interestRegisterService;
	private final InterestDeleteService interestDeleteService; 
	
	@PostMapping
	public ResponseEntity<RegisterInterest> reigster(@RequestBody RegisterInterest registerInterest, @LoginUser User user){
		registerInterest.setInterester(user);
		interestRegisterService.register(registerInterest);
		return new ResponseEntity<>(registerInterest, HttpStatus.CREATED);
	}
	
	@DeleteMapping
	public ResponseEntity<DeleteInterest> delete(@RequestBody DeleteInterest deleteInterest,@LoginUser User user){
		deleteInterest.setDeleter(user);
		interestDeleteService.delete(deleteInterest);
		return new ResponseEntity<>(deleteInterest, HttpStatus.OK);
	}
}
