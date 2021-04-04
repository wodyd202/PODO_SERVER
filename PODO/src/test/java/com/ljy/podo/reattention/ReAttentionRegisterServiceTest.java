package com.ljy.podo.reattention;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.attention.FakeAttentionRepository;
import com.ljy.podo.attention.aggregate.Attention;
import com.ljy.podo.attention.aggregate.exception.AttentionNotFoundException;
import com.ljy.podo.attention.infrastructure.AttentionRepository;
import com.ljy.podo.attention.service.registerAttention.RegisterAttention;
import com.ljy.podo.reAttention.aggregate.exception.InvalidReAttentionException;
import com.ljy.podo.reAttention.aggregate.exception.ReAttentionNotFoundException;
import com.ljy.podo.reAttention.infrastructure.ReAttentionRepository;
import com.ljy.podo.reAttention.service.loadReAttention.ReAttentionSearchDTO;
import com.ljy.podo.reAttention.service.registerReAttention.RegisterReAttention;
import com.ljy.podo.reAttention.service.registerReAttention.service.ReAttentionRegisterService;
import com.ljy.podo.reAttention.service.util.RegisterReAttentionValidator;

public class ReAttentionRegisterServiceTest {

	RegisterReAttentionValidator registerReAttentionValidator;
	ReAttentionRepository reAttentionRepository;
	AttentionRepository attentionRepository;
	ReAttentionRegisterService reAttentionRegisterService;
	
	@BeforeEach
	void setUp() {
		registerReAttentionValidator = new RegisterReAttentionValidator();
		reAttentionRepository = new FakeReAttentionRepository();
		attentionRepository = new FakeAttentionRepository();	
		attentionRepository.save(new Attention(new AttentionId("조언 아이디"), RegisterAttention.builder().build()));
		reAttentionRegisterService = new ReAttentionRegisterService(registerReAttentionValidator,reAttentionRepository,attentionRepository);
	}
	
	@Test
	@DisplayName("정상 케이스")
	void reigster_1() {
		RegisterReAttention registerReAttention = RegisterReAttention
				.builder()
				.attentionId("조언 아이디")
				.content("조언 답글 내용")
				.writer("wodyd202@naver.com")
				.build();
		
		reAttentionRegisterService.register(registerReAttention);
		
		ReAttentionSearchDTO searchDTO = ReAttentionSearchDTO
				.builder()
				.attentionId("조언 아이디")
				.build();
		long countAll = reAttentionRepository.countAll(searchDTO);
		assertThat(countAll).isEqualTo(1);
	}
	
	@Test
	@DisplayName("빈값이 존재할 때 에러")
	void reigster_2() {
		assertThrows(AttentionNotFoundException.class, ()->{
			RegisterReAttention registerReAttention = RegisterReAttention
					.builder()
					.attentionId("")
					.content("DSAKJDH")
					.writer("wodyd202@naver.com")
					.build();
			reAttentionRegisterService.register(registerReAttention);
		});
		assertThrows(InvalidReAttentionException.class, ()->{
			RegisterReAttention registerReAttention = RegisterReAttention
					.builder()
					.attentionId("조언 아이디")
					.content("")
					.writer("wodyd202@naver.com")
					.build();
			reAttentionRegisterService.register(registerReAttention);
		});
	}
	
	@Test
	@DisplayName("존재하지 않는 조언에 답글을 달때 에러")
	void reigster_3() {
		assertThrows(ReAttentionNotFoundException.class, ()->{
			RegisterReAttention registerReAttention = RegisterReAttention
					.builder()
					.attentionId("없는 조언")
					.content("DSAKJDH")
					.writer("wodyd202@naver.com")
					.build();
			reAttentionRegisterService.register(registerReAttention);
		});
	}
}
