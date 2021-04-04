package com.ljy.podo.reattention;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ljy.podo.reAttention.ReAttentionId;
import com.ljy.podo.reAttention.aggregate.ReAttention;
import com.ljy.podo.reAttention.aggregate.exception.InvalidReAttentionException;
import com.ljy.podo.reAttention.aggregate.exception.ReAttentionNotFoundException;
import com.ljy.podo.reAttention.infrastructure.ReAttentionRepository;
import com.ljy.podo.reAttention.service.registerReAttention.RegisterReAttention;
import com.ljy.podo.reAttention.service.updateReAttention.UpdateReAttention;
import com.ljy.podo.reAttention.service.updateReAttention.service.ReAttentionUpdateService;
import com.ljy.podo.reAttention.service.util.UpdateReAttentionValidator;

public class ReAttentionUpdateServiceTest {

	ReAttentionRepository reAttentionRepository;
	UpdateReAttentionValidator updateReAttentionValidator;
	ReAttentionUpdateService reAttentionUpdateService;
	
	@BeforeEach
	void setUp() {
		reAttentionRepository = new FakeReAttentionRepository();
		reAttentionRepository.save(new ReAttention(new ReAttentionId("조언 답글 아이디"),
				RegisterReAttention.builder().writer("wodyd202@naver.com").build()));
		
		updateReAttentionValidator = new UpdateReAttentionValidator();
		reAttentionUpdateService = new ReAttentionUpdateService(updateReAttentionValidator,
				reAttentionRepository);
	}

	@Test
	@DisplayName("정상 케이스")
	void update_1() {
		UpdateReAttention updateReAttention = UpdateReAttention.builder()
				.reAttentionId("조언 답글 아이디")
				.content("조언 답글 내용")
				.updater("wodyd202@naver.com").build();
		reAttentionUpdateService.register(updateReAttention);

		Optional<ReAttention> findById = reAttentionRepository.findById(new ReAttentionId("조언 답글 아이디"));
		if (!findById.isPresent()) {
			fail();
		}
		ReAttention reAttention = findById.get();
		assertThat(reAttention.getContent().toString()).isEqualTo("조언 답글 내용");
	}
	
	@Test
	@DisplayName("빈값을 입력했을 때 에러")
	void update_2() {
		assertThrows(InvalidReAttentionException.class, ()->{
			UpdateReAttention updateReAttention = UpdateReAttention.builder()
					.reAttentionId("조언 답글 아이디")
					.content("")
					.updater("wodyd202@naver.com").build();
			reAttentionUpdateService.register(updateReAttention);
		});
		assertThrows(ReAttentionNotFoundException.class, ()->{
			UpdateReAttention updateReAttention = UpdateReAttention.builder()
					.reAttentionId("")
					.content("조언 글")
					.updater("wodyd202@naver.com").build();
			reAttentionUpdateService.register(updateReAttention);
		});
	}

	@Test
	@DisplayName("존재하지 않는 조언 답글 수정시 에러")
	void update_3() {
		assertThrows(ReAttentionNotFoundException.class, ()->{
			UpdateReAttention updateReAttention = UpdateReAttention.builder()
					.reAttentionId("없는 조언 답글 아이디")
					.content("조언 답글 내용")
					.updater("wodyd202@naver.com").build();
			reAttentionUpdateService.register(updateReAttention);
		});
	}
}
