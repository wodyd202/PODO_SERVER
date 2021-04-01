package com.ljy.podo.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ljy.podo.user.aggregate.User;
import com.ljy.podo.user.infrastructure.UserRepository;
import com.ljy.podo.user.service.registerUser.RegisterUser;
import com.ljy.podo.user.service.updateUser.UpdateUser;
import com.ljy.podo.user.service.updateUser.service.UserUpdateService;
import com.ljy.podo.user.service.util.UpdateUserValiator;

/**
 * 
 * @author 이재용 
 *
 *	1. 존재하는 아이디인지 체크해야한다.
 *  2. 해당 전공이 존재하는 전공인지 확인해야한다.
 *
 */
public class UserUpdateServiceTest extends UserTest{

	UserRepository fakeUserRepository;
	UserUpdateService userUpdateService;
	
	@BeforeEach
	void setUp() {
		fakeUserRepository = new FakeUserRepository();
		userUpdateService = new UserUpdateService(new UpdateUserValiator() ,fakeUserRepository, new BCryptPasswordEncoder());
		RegisterUser registerUser = createUser("wodyd202@naver.com","testestestes","컴퓨터 공학");
		fakeUserRepository.save(registerUser.toEntity());
	}
	
	@Test
	@DisplayName("사용자 등록 성공 케이스")
	public void register_1() {
		UpdateUser updateUser = updateUser("wodyd202@naver.com", "update", "profile");
		userUpdateService.register(updateUser);
		User user = fakeUserRepository.findByEmail(new Email("wodyd202@naver.com")).get();
		assertThat(user.getPassword()).isEqualTo(new Password("update"));
	}
}
