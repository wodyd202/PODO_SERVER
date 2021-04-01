package com.ljy.podo.portfolio.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ljy.podo.common.ErrorResponse;
import com.ljy.podo.portfolio.aggregate.exception.InvalidPortfolioException;
import com.ljy.podo.portfolio.aggregate.exception.PortfolioNotFindException;

@RestControllerAdvice
public class PortfolioErrorController {

	@ExceptionHandler(InvalidPortfolioException.class)
	public ResponseEntity<ErrorResponse> InvalidPortfolioException(InvalidPortfolioException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMsg(), e.getField()));
	}

	@ExceptionHandler(PortfolioNotFindException.class)
	public ResponseEntity<ErrorResponse> PortfolioNotFindException(PortfolioNotFindException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(e.getMsg(), e.getField()));
	}
	
}
