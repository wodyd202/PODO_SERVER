package com.ljy.podo.reAttention.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ljy.podo.config.security.oauth.LoginUser;
import com.ljy.podo.reAttention.service.deleteReAttention.DeleteReAttention;
import com.ljy.podo.reAttention.service.deleteReAttention.service.ReAttentionDeleteService;
import com.ljy.podo.reAttention.service.registerReAttention.RegisterReAttention;
import com.ljy.podo.reAttention.service.registerReAttention.service.ReAttentionRegisterService;
import com.ljy.podo.reAttention.service.updateReAttention.UpdateReAttention;
import com.ljy.podo.reAttention.service.updateReAttention.service.ReAttentionUpdateService;
import com.ljy.podo.user.aggregate.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/re-attention")
@RequiredArgsConstructor
public class ReAttentionUpdateAPI {
	
	private final ReAttentionRegisterService reAttentionRegisterService;
	private final ReAttentionUpdateService reAttentionUpdateService;
	private final ReAttentionDeleteService reAttentionDeleteService;
	
	@PostMapping
	public ResponseEntity<RegisterReAttention> register(@RequestBody RegisterReAttention registerReAttention, @LoginUser User user){
		registerReAttention.setWriter(user);
		reAttentionRegisterService.register(registerReAttention);
		return new ResponseEntity<>(registerReAttention, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<UpdateReAttention> update(@RequestBody UpdateReAttention updateReAttention, @LoginUser User user){
		updateReAttention.setWriter(user);
		reAttentionUpdateService.register(updateReAttention);
		return new ResponseEntity<>(updateReAttention, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<DeleteReAttention> delete(@RequestBody DeleteReAttention deleteReAttention, @LoginUser User user){
		deleteReAttention.setWriter(user);
		reAttentionDeleteService.delete(deleteReAttention);
		return new ResponseEntity<>(deleteReAttention, HttpStatus.OK);
	}
	
}
