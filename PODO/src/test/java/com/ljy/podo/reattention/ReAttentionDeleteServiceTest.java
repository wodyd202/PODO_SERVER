package com.ljy.podo.reattention;

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
import com.ljy.podo.reAttention.service.deleteReAttention.DeleteReAttention;
import com.ljy.podo.reAttention.service.deleteReAttention.service.ReAttentionDeleteService;
import com.ljy.podo.reAttention.service.registerReAttention.RegisterReAttention;

public class ReAttentionDeleteServiceTest {
	
	ReAttentionRepository reAttentionRepository;
	ReAttentionDeleteService reAttentionDeleteService;

	@BeforeEach
	void setUp() {
		reAttentionRepository = new FakeReAttentionRepository();
		reAttentionRepository.save(new ReAttention(new ReAttentionId("조언 답글 아이디"),
				RegisterReAttention.builder().writer("wodyd202@naver.com").build()));
		reAttentionDeleteService = new ReAttentionDeleteService(reAttentionRepository);
	}
	
	@Test
	@DisplayName("정상 케이스")
	void delete_1() {
		DeleteReAttention deleteReAttention = DeleteReAttention
				.builder()
				.reAttentionId("조언 답글 아이디")
				.deleter("wodyd202@naver.com").build();
		reAttentionDeleteService.delete(deleteReAttention);
		Optional<ReAttention> findById = reAttentionRepository.findById(new ReAttentionId("조언 답글 아이디"));
		if(findById.isPresent()) {
			fail();
		}
	}
	
	@Test
	@DisplayName("존재하지 않는 조언 답글 삭제")
	void delete_2() {
		assertThrows(ReAttentionNotFoundException.class, ()->{
			DeleteReAttention deleteReAttention = DeleteReAttention
					.builder()
					.reAttentionId("없는 조언 답글")
					.deleter("wodyd202@naver.com").build();
			reAttentionDeleteService.delete(deleteReAttention);
		});
	}

	@Test
	@DisplayName("자신의 조언 답글이 아닌 경우")
	void delete_3() {
		assertThrows(InvalidReAttentionException.class, ()->{
			DeleteReAttention deleteReAttention = DeleteReAttention
					.builder()
					.reAttentionId("조언 답글 아이디")
					.deleter("test@naver.com").build();
			reAttentionDeleteService.delete(deleteReAttention);
		});
	}
}
