package com.ljy.podo.reAttention.service.loadReAttention;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReAttentionSearchDTO {
	private String attentionId;
	private int page;
	private int size;
}
