package com.ljy.podo.portfolio.aggregate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PortfolioNotFindException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	private String msg;
	private String field;
}
