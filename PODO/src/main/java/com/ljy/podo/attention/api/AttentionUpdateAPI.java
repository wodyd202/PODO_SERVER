package com.ljy.podo.attention.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ljy.podo.attention.service.registerAttention.RegisterAttention;
import com.ljy.podo.attention.service.registerAttention.service.AttentionRegisterService;
import com.ljy.podo.attention.service.updateAttention.UpdateAttention;
import com.ljy.podo.attention.service.updateAttention.service.AttentionUpdateService;
import com.ljy.podo.config.security.oauth.LoginUser;
import com.ljy.podo.user.aggregate.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/attention")
@RequiredArgsConstructor
public class AttentionUpdateAPI {
	
	private final AttentionRegisterService attentionRegisterService;
	private final AttentionUpdateService attentionUpdateService;
	
	@PostMapping
	public ResponseEntity<RegisterAttention> register(@RequestBody RegisterAttention registerAttention, @LoginUser User user){
		registerAttention.setWriter(user);
		attentionRegisterService.register(registerAttention);
		return new ResponseEntity<>(registerAttention,HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<UpdateAttention> update(@RequestBody UpdateAttention updateAttention, @LoginUser User user){
		updateAttention.setUpdater(user);
		attentionUpdateService.register(updateAttention);
		return new ResponseEntity<>(updateAttention,HttpStatus.OK);
	}
}
