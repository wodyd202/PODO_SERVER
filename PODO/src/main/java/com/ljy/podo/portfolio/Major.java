package com.ljy.podo.portfolio;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Major {
	private String value;
	
	@Override
	public String toString() {
		return this.value;
	}
}
