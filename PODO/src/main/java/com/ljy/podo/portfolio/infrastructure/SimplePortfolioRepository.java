package com.ljy.podo.portfolio.infrastructure;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ljy.podo.attention.AttentionState;
import com.ljy.podo.attention.aggregate.QAttention;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.ShowType;
import com.ljy.podo.portfolio.Writer;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.aggregate.QPortfolio;
import com.ljy.podo.portfolio.aggregate.QPortfolioDetail;
import com.ljy.podo.portfolio.service.loadPortfolio.PortfolioSearchDTO;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioFullData;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioListData;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class SimplePortfolioRepository implements PortfolioRepository{

	@PersistenceContext
	private EntityManager em;
	
	private QPortfolio portfolio = QPortfolio.portfolio;
	private QPortfolioDetail portfolioDetail = QPortfolioDetail.portfolioDetail;
	private QAttention attention = QAttention.attention;
	
	@Override
	public void save(Portfolio portfolio) {
		em.persist(portfolio);
		em.persist(portfolio.getDetail());
	}

	@Override
	public long countAll(PortfolioSearchDTO searchDTO) {
		JPAQuery<Portfolio> query = new JPAQuery<>(em);
		return query.from(portfolio).where(createBooleanExpression(searchDTO)).fetchCount();
	}

	@Override
	public Optional<Portfolio> findById(PortfolioId portfolioId) {
		JPAQuery<Portfolio> query = new JPAQuery<>(em);
		return Optional.ofNullable(query.from(portfolio).where(portfolio.id().eq(portfolioId)).fetchOne());
	}

	@Override
	public Optional<PortfolioFullData> findPortfolioViewDataById(PortfolioId portfolioId) {
		JPAQuery<PortfolioFullData> query = new JPAQuery<>(em);
		query = query
				.from(portfolio)
				.select(Projections.constructor(PortfolioFullData.class,
					portfolio.id().value,
					portfolio.title().value,
					portfolio.writer().value,
					portfolio.major().value,
					portfolio.header().value,
					portfolio.detail().content().value,
					portfolio.createDate,
					portfolio.detail().lastModify,
					portfolioDetail.images().value,
					portfolio.state,
					portfolio.showType
				))
				.where(portfolio.id().eq(portfolioId))
				.innerJoin(portfolio.detail(), portfolioDetail);
		return Optional.ofNullable(query.fetchOne());
	}
	
	@Override
	public List<PortfolioListData> findAll(PortfolioSearchDTO searchDTO) {
		JPAQuery<PortfolioListData> query = new JPAQuery<>(em);
		query = query.from(portfolio)
				.select(Projections.constructor(PortfolioListData.class, 
						portfolio.id().value,
						portfolio.title().value,
						portfolio.writer().value,
						portfolio.major().value,
						portfolio.header().value,
						portfolioDetail.content().value,
						portfolio.createDate,
						portfolioDetail.images().value,
						portfolio.state,
						ExpressionUtils.as(
								JPAExpressions.select(attention.count())
												.from(attention)
												.where(attention.portfolioId().eq(portfolio.id()).and(attention.state.eq(AttentionState.CREATE)))
						, "attentionCnt")
					))
				.where(createBooleanExpression(searchDTO))
				.innerJoin(portfolio.detail(), portfolioDetail)
				.limit(searchDTO.getSize())
				.offset(searchDTO.getSize() * searchDTO.getPage())
				.orderBy(portfolio.createDate.desc());
		return query.fetch();
	}

	private BooleanBuilder createBooleanExpression(PortfolioSearchDTO searchDTO) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(portfolio.state.eq(searchDTO.getState()));
		if(searchDTO.getShowType() == null) {
			builder.and(portfolio.showType.eq(ShowType.PUBLIC));
		}else {
			builder.and(portfolio.showType.eq(searchDTO.getShowType()));
		}
		if(searchDTO.getEmail() != null) {
			builder.and(portfolio.writer().eq(new Writer(searchDTO.getEmail())));
		}
		if(searchDTO.getStartDate() != null && searchDTO.getEndDate() != null) {
			builder.and(portfolio.createDate.between(searchDTO.getStartDate(), searchDTO.getEndDate()));
		}
		return builder;
	}

}
