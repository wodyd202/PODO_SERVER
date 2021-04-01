package com.ljy.podo.attention.service.loadAttention;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttentionSearchDTO {
	private String portfolioId;
	private int page;
	private int size;
}
