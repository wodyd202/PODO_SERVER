package com.ljy.podo.interest;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interester implements Serializable{
	private static final long serialVersionUID = 1L;
	private String value;
	
	@Override
	public String toString() {
		return this.value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Interester) {
			return ((Interester) obj).value.equals(this.value);
		}
		return false;
	}
}
