package com.ljy.podo.attention.service.loadAttention.projection;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttentionList {
	private List<AttentionListData> content;
	private long totalElement;
}
