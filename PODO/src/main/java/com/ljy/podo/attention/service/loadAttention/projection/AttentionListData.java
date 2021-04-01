package com.ljy.podo.attention.service.loadAttention.projection;

import java.time.LocalDateTime;

import com.ljy.podo.attention.AttentionId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttentionListData {
	private String id;
	private String content;
	private String writer;
	private LocalDateTime createDate;
	
	public AttentionListData(AttentionId id, String content, String writer, LocalDateTime createDate) {
		this.id = id.toString();
		this.content = content;
		this.writer = writer;
		this.createDate = createDate;
	}
}
