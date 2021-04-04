package com.ljy.podo.attention.infrastructure;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.attention.AttentionState;
import com.ljy.podo.attention.aggregate.Attention;
import com.ljy.podo.attention.aggregate.QAttention;
import com.ljy.podo.attention.service.loadAttention.AttentionSearchDTO;
import com.ljy.podo.attention.service.loadAttention.projection.AttentionListData;
import com.ljy.podo.portfolio.PortfolioId;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
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
		query = query.from(attention).where(createBooleanExpression(search));
		return query.fetchCount();
	}

	@Override
	public List<AttentionListData> findAll(AttentionSearchDTO search) {
		JPAQuery<AttentionListData> query = new JPAQuery<>(em);
		query = query.from(attention).where(createBooleanExpression(search))
				.select(Projections.constructor(AttentionListData.class, 
						attention.attentionId().value,
						attention.content().value,
						attention.writer().value,
						attention.createDate
					));
		query = query.limit(search.getSize()).offset(search.getPage() * search.getSize()).orderBy(attention.createDate.desc());
		return query.fetch();
	}
	
	@Override
	public Optional<Attention> findById(AttentionId attentionId) {
		JPAQuery<Attention> query = new JPAQuery<>(em);
		return Optional.ofNullable(query.from(attention).where(attention.attentionId().eq(attentionId)).fetchOne());
	}
	
	private BooleanBuilder createBooleanExpression(AttentionSearchDTO dto) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(attention.state.eq(AttentionState.CREATE));
		if(dto.getPortfolioId() != null) {
			builder.and(attention.portfolioId().eq(new PortfolioId(dto.getPortfolioId())));
		}
		return builder;
	}

}
