package com.ljy.podo.attention.service.registerAttention;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.attention.aggregate.Attention;
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
public class RegisterAttention {
	private String portfolioId;
	private String content;
	private String writer;
	
	public void setWriter(User user) {
		this.writer = user.getEmail().toString();
	}
	
	public Attention toEntity(AttentionId id) {
		return new Attention(id, this);
	}
}
