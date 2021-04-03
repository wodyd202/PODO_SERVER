package com.ljy.podo.interest.aggregate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidInterestException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	private String msg;
	private String field;
}
