package com.ljy.podo.user;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email implements Serializable {
	private static final long serialVersionUID = 1L;
	private String value;

	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Email) {
			return ((Email) obj).value.equals(this.value);
		}
		return false;
	}
}
