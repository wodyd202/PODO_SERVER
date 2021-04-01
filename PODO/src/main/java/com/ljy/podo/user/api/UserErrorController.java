package com.ljy.podo.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ljy.podo.common.ErrorResponse;
import com.ljy.podo.user.aggregate.exception.DupUserException;
import com.ljy.podo.user.aggregate.exception.InvalidUserException;

@RestControllerAdvice
public class UserErrorController {

	@ExceptionHandler(DupUserException.class)
	public ResponseEntity<ErrorResponse> DupUserException(DupUserException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), e.getField()));
	}
	
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<ErrorResponse> InvalidUserException(InvalidUserException e){
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMsg(), e.getField()));
	}
}
