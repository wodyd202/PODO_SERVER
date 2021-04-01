package com.ljy.podo.attention.service.deleteAttention;

import com.ljy.podo.user.aggregate.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeleteAttention {
	private String attentionId;
	private String deleter;
	
	public void setDeleter(User user) {
		this.deleter = user.getEmail().toString();
	}
}
