package com.ljy.podo.user.infrastructure;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ljy.podo.user.Email;
import com.ljy.podo.user.aggregate.QUser;
import com.ljy.podo.user.aggregate.User;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class SimpleUserRepository implements UserRepository{
	
	@PersistenceContext
	private EntityManager em;
	
	private QUser user = QUser.user;
	
	@Override
	public void save(User user) {
		em.persist(user);
	}

	@Override
	public Optional<User> findByEmail(Email email) {
		JPAQuery<User> query = new JPAQuery<>(em);
		return Optional.ofNullable(
				query.from(user)
					.where(user.email.eq(email))
					.fetchOne()
				);
	}

}
