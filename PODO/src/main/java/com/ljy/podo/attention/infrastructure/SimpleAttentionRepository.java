package com.ljy.podo.attention.infrastructure;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.attention.aggregate.Attention;
import com.ljy.podo.attention.aggregate.QAttention;
import com.ljy.podo.attention.service.loadAttention.AttentionSearchDTO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class SimpleAttentionRepository implements AttentionRepository {

	@PersistenceContext
	private EntityManager em;

	private QAttention attention = QAttention.attention;

	@Override
	public void save(Attention attention) {
		em.persist(attention);
	}

	@Override
	public long countAll(AttentionSearchDTO search) {
		JPAQuery<Attention> query = new JPAQuery<>(em);
		query = query.where(createBooleanExpression(search));
		query = query.limit(search.getSize()).offset(search.getPage() * search.getSize());
		return query.fetchCount();
	}

	@Override
	public Optional<Attention> findById(AttentionId attentionId) {
		JPAQuery<Attention> query = new JPAQuery<>();
		return Optional.ofNullable(query.from(attention).where(attention.attentionId.eq(attentionId)).fetchOne());
	}
	
	private BooleanExpression createBooleanExpression(AttentionSearchDTO dto) {
		BooleanExpression expression = null;
		if(dto.getPortfolioId() != null) {
			expression = attention.portfolioId().eq(dto.getPortfolioId());
		}
		return expression;
	}

}
