package com.ljy.podo.reAttention.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ljy.podo.reAttention.infrastructure.SimpleReAttentionRepository;
import com.ljy.podo.reAttention.service.loadReAttention.ReAttentionSearchDTO;
import com.ljy.podo.reAttention.service.loadReAttention.projection.ReAttentionList;
import com.ljy.podo.reAttention.service.loadReAttention.projection.ReAttentionListData;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/re-attention")
@RequiredArgsConstructor
public class ReAttentionLoadAPI {
	private final SimpleReAttentionRepository reAttentionRepository;
	
	@GetMapping
	public ResponseEntity<ReAttentionList> findAll(ReAttentionSearchDTO searchDTO){
		long totalElement = reAttentionRepository.countAll(searchDTO);
		List<ReAttentionListData> findAll = reAttentionRepository.findAll(searchDTO);
		return new ResponseEntity<>(new ReAttentionList(totalElement, findAll), HttpStatus.OK);
	}
}
