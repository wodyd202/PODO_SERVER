package com.ljy.podo.user.aggregate.exception;

import lombok.Getter;

@Getter
public class DupUserException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	private String field;

	public DupUserException(String msg, String field) {
		super(msg);
		this.field = field;
	}
}
