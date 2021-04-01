package com.ljy.podo.attention.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ljy.podo.attention.aggregate.exception.InvalidAttentionException;
import com.ljy.podo.common.ErrorResponse;

@RestControllerAdvice
public class AttentionErrorController {

	@ExceptionHandler(InvalidAttentionException.class)
	public ResponseEntity<ErrorResponse> InvalidAttentionException(InvalidAttentionException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMsg(), e.getField()));
	}
	
}
