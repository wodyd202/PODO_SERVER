package com.ljy.podo.reAttention.infrastructure;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ljy.podo.attention.AttentionId;
import com.ljy.podo.reAttention.ReAttentionId;
import com.ljy.podo.reAttention.ReAttentionState;
import com.ljy.podo.reAttention.aggregate.QReAttention;
import com.ljy.podo.reAttention.aggregate.ReAttention;
import com.ljy.podo.reAttention.service.loadReAttention.ReAttentionSearchDTO;
import com.ljy.podo.reAttention.service.loadReAttention.projection.ReAttentionListData;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class SimpleReAttentionRepository implements ReAttentionRepository{

	@PersistenceContext
	private EntityManager em;
	
	private QReAttention reAttention = QReAttention.reAttention;
	
	@Override
	public void save(ReAttention entity) {
		em.persist(entity);
	}

	@Override
	public long countAll(ReAttentionSearchDTO searchDTO) {
		JPAQuery<ReAttention> query = new JPAQuery<>(em);
		query.from(reAttention)
			.where(reAttention.attentionId().eq(new AttentionId(searchDTO.getAttentionId())));
		return query.fetchCount();
	}

	@Override
	public List<ReAttentionListData> findAll(ReAttentionSearchDTO searchDTO) {
		JPAQuery<ReAttentionListData> query = new JPAQuery<>(em);
		query.from(reAttention)
				.select(Projections.constructor(ReAttentionListData.class, 
						reAttention.reAttentionId,
						reAttention.content().value,
						reAttention.writer().value,
						reAttention.createDate
					))
				.where(reAttention.attentionId().eq(new AttentionId(searchDTO.getAttentionId())).and(reAttention.state.eq(ReAttentionState.CREATE)))
				.limit(searchDTO.getSize())
				.offset(searchDTO.getSize() * searchDTO.getPage())
				.orderBy(reAttention.createDate.desc());
		return query.fetch();
	}
	
	@Override
	public Optional<ReAttention> findById(ReAttentionId reAttentionId) {
		JPAQuery<ReAttention> query = new JPAQuery<>(em);
		query.from(reAttention)
			.where(reAttention.reAttentionId.eq(reAttentionId));
		return Optional.ofNullable(query.fetchOne());
	}

}
