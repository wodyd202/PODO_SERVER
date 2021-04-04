package com.ljy.podo.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ljy.podo.user.aggregate.User;
import com.ljy.podo.user.infrastructure.UserRepository;

public class FakeUserRepository implements UserRepository {

	private final Map<String, User> repository = new HashMap<>();
	
	@Override
	public void save(User user) {
		repository.put(user.getEmail().toString(), user);
	}

	@Override
	public Optional<User> findByEmail(Email email) {
		return Optional.ofNullable(repository.get(email.toString()));
	}

	@Override
	public long countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

}
