package com.ljy.podo.reAttention.service.registerReAttention;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ljy.podo.reAttention.ReAttentionId;
import com.ljy.podo.reAttention.aggregate.ReAttention;
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
public class RegisterReAttention {
	private String attentionId;
	private String content;
	
	@JsonIgnore
	private String writer;

	public void setWriter(User writer) {
		this.writer = writer.getEmail().toString();
	}
	
	public ReAttention toEntity(ReAttentionId reAttentionId) {
		return new ReAttention(reAttentionId,this);
	}
}
