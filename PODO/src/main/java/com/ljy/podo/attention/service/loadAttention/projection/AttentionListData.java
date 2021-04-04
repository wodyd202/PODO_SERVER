package com.ljy.podo.attention.service.loadAttention.projection;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttentionListData {
	private String id;
	private String content;
	private String writer;
	private LocalDateTime createDate;
}
