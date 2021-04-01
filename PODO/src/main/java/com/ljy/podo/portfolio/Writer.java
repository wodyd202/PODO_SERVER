package com.ljy.podo.portfolio;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Writer {
	private String value;

	@Override
	public String toString() {
		return this.value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Writer) {
			return ((Writer) obj).toString().equals(this.value);
		}
		return false;
	}
}
