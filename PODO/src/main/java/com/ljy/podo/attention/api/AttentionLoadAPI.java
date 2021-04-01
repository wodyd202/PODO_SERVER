package com.ljy.podo.attention.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ljy.podo.attention.aggregate.exception.InvalidAttentionException;
import com.ljy.podo.attention.infrastructure.SimpleAttentionRepository;
import com.ljy.podo.attention.service.loadAttention.AttentionSearchDTO;
import com.ljy.podo.attention.service.loadAttention.projection.AttentionList;
import com.ljy.podo.attention.service.loadAttention.projection.AttentionListData;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/attention")
@RequiredArgsConstructor
public class AttentionLoadAPI {
	private final SimpleAttentionRepository attentionRepository;

	@GetMapping
	public ResponseEntity<AttentionList> findAllByPortfolioId(AttentionSearchDTO searchDTO) {
		if (searchDTO.getPortfolioId() == null || searchDTO.getPortfolioId().isEmpty()) {
			throw new InvalidAttentionException("포트폴리오 고유 번호를 입력해주세요.", "portfolio");
		}
		List<AttentionListData> attentionList = attentionRepository.findAll(searchDTO);
		long attentionCount = attentionRepository.countAll(searchDTO);
		return new ResponseEntity<>(new AttentionList(attentionList, attentionCount), HttpStatus.OK);
	}
}
