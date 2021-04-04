package com.ljy.podo.interest.infrastructure;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ljy.podo.interest.Interester;
import com.ljy.podo.interest.aggregate.Interest;
import com.ljy.podo.interest.aggregate.QInterest;
import com.ljy.podo.interest.service.loadInterest.InterestSearchDTO;
import com.ljy.podo.interest.service.loadInterest.projection.InterestFullData;
import com.ljy.podo.portfolio.PortfolioId;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class SimpleInterestRepository implements InterestRepository {

	@PersistenceContext
	private EntityManager em;

	private QInterest interest = QInterest.interest;

	@Override
	public void save(Interest entity) {
		em.persist(entity);
	}

	@Override
	public long countAll() {
		JPAQuery<InterestFullData> query = new JPAQuery<>(em);
		query.from(interest);
		return query.fetchCount();
	}
	
	@Override
	public InterestFullData findByPortfolioId(InterestSearchDTO searchDTO) {
		JPAQuery<InterestFullData> query = new JPAQuery<>(em);
		return query.from(interest)
				.where(interest.portfolioId().eq(new PortfolioId(searchDTO.getPortfolioId()))
						.and(interest.interester().eq(new Interester(searchDTO.getInterester()))))
				.select(Projections.constructor(InterestFullData.class, interest.portfolioId().value,
						interest.interester().value, interest.createDate))
				.fetchOne();
	}

	@Override
	public void remove(InterestFullData findByPortfolioId) {
		JPADeleteClause query = new JPADeleteClause(em, interest);
		query.where(interest.portfolioId().eq(new PortfolioId(findByPortfolioId.getPortfolioId()))
				.and(interest.interester().eq(new Interester(findByPortfolioId.getInterester())))).execute();
	}


}
