package com.ljy.podo.attention.aggregate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttentionNotFoundException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	private String msg;
	private String field;
}
