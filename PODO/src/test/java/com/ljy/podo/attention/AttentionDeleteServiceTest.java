package com.ljy.podo.attention;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ljy.podo.attention.aggregate.Attention;
import com.ljy.podo.attention.aggregate.exception.InvalidAttentionException;
import com.ljy.podo.attention.infrastructure.AttentionRepository;
import com.ljy.podo.attention.service.deleteAttention.DeleteAttention;
import com.ljy.podo.attention.service.deleteAttention.service.AttentionDeleteService;
import com.ljy.podo.attention.service.registerAttention.RegisterAttention;

public class AttentionDeleteServiceTest {

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
	@DisplayName("정상 케이스")
	void delete_1() {
		DeleteAttention deleteAttention = DeleteAttention.
				builder()
				.attentionId("조언 아이디")
				.deleter("wodyd202@naver.com")
				.build();
		AttentionDeleteService attentionDeleteService = new AttentionDeleteService(attentionRepository);
		attentionDeleteService.delete(deleteAttention);
		Optional<Attention> findById = attentionRepository.findById(new AttentionId("조언 아이디"));
		if(findById.isPresent() && !findById.get().isDelete()) {
			fail();
		}
	}
	
	@Test
	@DisplayName("자신의 조언이 아닌경우 에러")
	void delete_2() {
		assertThrows(InvalidAttentionException.class, ()->{
			DeleteAttention deleteAttention = DeleteAttention.
					builder()
					.attentionId("조언 아이디")
					.deleter("test@naver.com")
					.build();
			AttentionDeleteService attentionDeleteService = new AttentionDeleteService(attentionRepository);
			attentionDeleteService.delete(deleteAttention);
		});
	}
}
