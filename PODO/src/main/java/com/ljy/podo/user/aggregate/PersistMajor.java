package com.ljy.podo.user.aggregate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "major")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersistMajor {
	
	@Id
	private String name;
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PersistMajor) {
			return ((PersistMajor) obj).name.equals(this.name);
		}
		return false;
	}
}
