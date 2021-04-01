package com.ljy.podo.attention;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ljy.podo.attention.aggregate.Attention;
import com.ljy.podo.attention.aggregate.exception.InvalidAttentionException;
import com.ljy.podo.attention.infrastructure.AttentionRepository;
import com.ljy.podo.attention.service.registerAttention.RegisterAttention;
import com.ljy.podo.attention.service.updateAttention.UpdateAttention;
import com.ljy.podo.attention.service.updateAttention.service.AttentionUpdateService;
import com.ljy.podo.attention.service.util.UpdateAttentionValidator;
import com.ljy.podo.portfolio.PortfolioTest;

public class AttentionUpdateServiceTest implements PortfolioTest{
	
	AttentionRepository attentionRepository;

	@BeforeEach
	void setUp() {
		attentionRepository = new FakeAttentionRepository();
		RegisterAttention registerAttention = RegisterAttention
				.builder()
				.portfolioId("아이디")
				.content("조언")
				.writer("wodyd202@naver.com")
				.build();
		attentionRepository.save(registerAttention.toEntity(new AttentionId("조언 아이디")));
	}
	
	@Test
	@DisplayName("정상 수정 케이스")
	void update_1() {
		UpdateAttention updateAttention = UpdateAttention
				.builder()
				.attentionId("조언 아이디")
				.content("수정 조언")
				.updater("wodyd202@naver.com")
				.build();
		UpdateAttentionValidator updateAttentionValidator = new UpdateAttentionValidator();
		AttentionUpdateService attentionUpdateService = new AttentionUpdateService(updateAttentionValidator,attentionRepository);
		attentionUpdateService.register(updateAttention);
		
		Optional<Attention> findById = attentionRepository.findById(new AttentionId("조언 아이디"));
		if(!findById.isPresent()) {
			fail();
		}
		
		Attention attention = findById.get();
		
		assertThat(attention.getContent().toString()).isEqualTo("수정 조언");
	}
	
	@Test
	@DisplayName("빈값이 존재할 때 에러")
	void update_2() {
		assertThrows(InvalidAttentionException.class, ()->{
			UpdateAttention updateAttention = UpdateAttention
					.builder()
					.attentionId("조언 아이디")
					.content("")
					.updater("wodyd202@naver.com")
					.build();
			UpdateAttentionValidator updateAttentionValidator = new UpdateAttentionValidator();
			AttentionUpdateService attentionUpdateService = new AttentionUpdateService(updateAttentionValidator,attentionRepository);
			attentionUpdateService.register(updateAttention);
		});
	}
}
