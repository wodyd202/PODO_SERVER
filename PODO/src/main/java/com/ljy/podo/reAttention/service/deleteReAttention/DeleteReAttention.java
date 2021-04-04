package com.ljy.podo.reAttention.service.deleteReAttention;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ljy.podo.user.aggregate.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteReAttention {
	private String reAttentionId;
	
	@JsonIgnore
	private String deleter;

	public void setWriter(User user) {
		this.deleter = user.getEmail().toString();
	}
}
