package com.ljy.podo.attention.service.updateAttention;

import com.ljy.podo.user.aggregate.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAttention {
	private String attentionId;
	private String content;
	private String updater;
	
	public void setUpdater(User user) {
		this.updater = user.getEmail().toString();
	}
}
