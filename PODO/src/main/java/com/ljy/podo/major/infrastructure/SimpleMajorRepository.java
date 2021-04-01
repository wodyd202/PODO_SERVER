package com.ljy.podo.major.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ljy.podo.major.projection.MajorListData;
import com.ljy.podo.user.Major;
import com.ljy.podo.user.aggregate.PersistMajor;
import com.ljy.podo.user.aggregate.QPersistMajor;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class SimpleMajorRepository implements MajorRepository{

	@PersistenceContext
	private EntityManager em;
	
	private QPersistMajor persistMajor = QPersistMajor.persistMajor;
	
	@Override
	@Transactional
	public void save(Major major) {
		em.persist(new PersistMajor(major.toString()));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existByName(Major major) {
		JPAQuery<PersistMajor> query = new JPAQuery<>(em);
		return query.from(persistMajor)
					.where(persistMajor
							.eq(new PersistMajor(major.toString())))
					.fetchCount() != 0;
	}

	@Override
	public List<MajorListData> findAll() {
		JPAQuery<PersistMajor> query = new JPAQuery<>(em);
		return query.from(persistMajor)
			.select(Projections.constructor(MajorListData.class, 
					persistMajor.name
				))
			.fetch();
	}

}
