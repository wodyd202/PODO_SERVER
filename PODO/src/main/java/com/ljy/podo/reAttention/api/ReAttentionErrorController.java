package com.ljy.podo.reAttention.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ljy.podo.common.ErrorResponse;
import com.ljy.podo.reAttention.aggregate.exception.InvalidReAttentionException;
import com.ljy.podo.reAttention.aggregate.exception.ReAttentionNotFoundException;

@RestControllerAdvice
public class ReAttentionErrorController {

	@ExceptionHandler(InvalidReAttentionException.class)
	public ResponseEntity<ErrorResponse> InvalidReAttentionException(InvalidReAttentionException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMsg(), e.getField()));
	}

	@ExceptionHandler(ReAttentionNotFoundException.class)
	public ResponseEntity<ErrorResponse> ReAttentionNotFoundException(ReAttentionNotFoundException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMsg(), e.getField()));
	}

}
