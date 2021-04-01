package com.ljy.podo.attention;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ljy.podo.attention.aggregate.exception.InvalidAttentionException;
import com.ljy.podo.attention.infrastructure.AttentionRepository;
import com.ljy.podo.attention.service.loadAttention.AttentionSearchDTO;
import com.ljy.podo.attention.service.registerAttention.RegisterAttention;
import com.ljy.podo.attention.service.registerAttention.service.AttentionRegisterService;
import com.ljy.podo.attention.service.util.RegisterAttentionValidator;
import com.ljy.podo.portfolio.FakePortfolioRepository;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.PortfolioTest;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.aggregate.exception.PortfolioNotFindException;
import com.ljy.podo.portfolio.infrastructure.PortfolioRepository;

public class AttentionRegisterServiceTest implements PortfolioTest{
	
	PortfolioRepository portfolioRepository;
	RegisterAttentionValidator attentionValidator;
	AttentionRepository attentionRepository;
	AttentionRegisterService attentionRegisterService;

	@BeforeEach
	void setUp() {
		attentionValidator = new RegisterAttentionValidator();
		attentionRepository = new FakeAttentionRepository();
		
		portfolioRepository = new FakePortfolioRepository();
		Portfolio createMockPortfolio = createMockPortfolio("아이디");
		portfolioRepository.save(createMockPortfolio);

		attentionRegisterService = new AttentionRegisterService(attentionValidator,portfolioRepository,attentionRepository);
	}
	
	@Test
	@DisplayName("정상 테스트")
	void register_1() {
		RegisterAttention registerAttention = RegisterAttention
				.builder()
				.portfolioId("아이디")
				.content("내용")
				.writer("다른사람@naver.com")
				.build();
		attentionRegisterService.register(registerAttention);
		
		AttentionSearchDTO search = AttentionSearchDTO
				.builder()
				.portfolioId(new PortfolioId("아이디"))
				.page(0)
				.size(10)
				.build();
		long count = attentionRepository.countAll(search);
		assertThat(count).isEqualTo(1);
	}
	
	@Test
	@DisplayName("빈값이 존재할 때 에러")
	public void register_2() {
		assertThrows(InvalidAttentionException.class, ()->{
			RegisterAttention registerAttention = RegisterAttention
					.builder()
					.portfolioId("")
					.content("내용")
					.writer("다른사람@naver.com")
					.build();
			attentionRegisterService.register(registerAttention);
		});
		assertThrows(InvalidAttentionException.class, ()->{
			RegisterAttention registerAttention = RegisterAttention
					.builder()
					.portfolioId("아이디")
					.content("")
					.writer("다른사람@naver.com")
					.build();
			attentionRegisterService.register(registerAttention);
		});
	}
	
	@Test
	@DisplayName("존재하지 않는 포트폴리오일때 에러")
	public void reigster_3() {
		assertThrows(PortfolioNotFindException.class, ()->{
			RegisterAttention registerAttention = RegisterAttention
					.builder()
					.portfolioId("없는 포트폴리오")
					.content("콘텐츠")
					.writer("다른사람@naver.com")
					.build();
			attentionRegisterService.register(registerAttention);
		});
	}

	@Test
	@DisplayName("자신의 포트폴리오에 조언을 등록할 수 없음")
	public void reigster_4() {
		assertThrows(InvalidAttentionException.class, ()->{
			RegisterAttention registerAttention = RegisterAttention
					.builder()
					.portfolioId("아이디")
					.content("콘텐츠")
					.writer("wodyd202@naver.com")
					.build();
			attentionRegisterService.register(registerAttention);
		});
	}
}
