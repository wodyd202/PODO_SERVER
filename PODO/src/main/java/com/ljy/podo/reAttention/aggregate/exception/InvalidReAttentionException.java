package com.ljy.podo.reAttention.aggregate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidReAttentionException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	private String msg;
	private String field;
}
