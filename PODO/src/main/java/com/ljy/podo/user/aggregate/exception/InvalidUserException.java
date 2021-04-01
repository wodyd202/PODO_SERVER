package com.ljy.podo.user.aggregate.exception;

import lombok.Getter;

@Getter
public class InvalidUserException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	private String msg;
	private String field;

	public InvalidUserException(String msg, String field) {
		this.msg = msg;
		this.field = field;
	}
}
