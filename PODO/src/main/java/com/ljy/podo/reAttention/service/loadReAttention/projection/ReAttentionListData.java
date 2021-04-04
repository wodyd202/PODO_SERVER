package com.ljy.podo.reAttention.service.loadReAttention.projection;

import java.util.Date;

import com.ljy.podo.reAttention.ReAttentionId;

import lombok.Getter;

@Getter
public class ReAttentionListData {
	private String id;
	private String content;
	private String writer;
	private Date createDate;

	public ReAttentionListData(ReAttentionId id, String content, String writer, Date createDate) {
		this.id = id.getValue();
		this.content = content;
		this.writer = writer;
		this.createDate = createDate;
	}
}
