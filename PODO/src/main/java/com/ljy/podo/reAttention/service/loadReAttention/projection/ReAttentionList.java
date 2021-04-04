package com.ljy.podo.reAttention.service.loadReAttention.projection;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReAttentionList {
	private long totalElement;
	private List<ReAttentionListData> content;
}
