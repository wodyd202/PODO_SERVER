package com.ljy.podo.user.infrastructure;

import java.util.Optional;

import com.ljy.podo.user.Email;
import com.ljy.podo.user.aggregate.User;

public interface UserRepository {
	void save(User user);

	Optional<User> findByEmail(Email email);
}
