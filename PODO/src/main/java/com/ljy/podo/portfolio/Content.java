package com.ljy.podo.portfolio;

import javax.persistence.Lob;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {
	
	@Lob
	private String value;

	@Override
	public String toString() {
		return this.value;
	}
}
