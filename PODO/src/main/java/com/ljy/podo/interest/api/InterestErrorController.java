package com.ljy.podo.interest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ljy.podo.common.ErrorResponse;
import com.ljy.podo.interest.aggregate.exception.InvalidInterestException;

@RestControllerAdvice
public class InterestErrorController {

	@ExceptionHandler(InvalidInterestException.class)
	public ResponseEntity<ErrorResponse> InvalidInterestException(InvalidInterestException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMsg(), e.getField()));
	}

}
