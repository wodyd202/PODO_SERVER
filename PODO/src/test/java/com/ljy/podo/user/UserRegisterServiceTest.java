package com.ljy.podo.user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ljy.podo.major.infrastructure.MajorRepository;
import com.ljy.podo.user.aggregate.User;
import com.ljy.podo.user.aggregate.exception.DupUserException;
import com.ljy.podo.user.aggregate.exception.InvalidUserException;
import com.ljy.podo.user.infrastructure.UserRepository;
import com.ljy.podo.user.service.registerUser.RegisterUser;
import com.ljy.podo.user.service.registerUser.service.UserRegisterService;
import com.ljy.podo.user.service.util.RegisterUserValiator;

/**
 * 
 * @author 이재용 
 *
 *	1. 중복된 아이디인지 체크해야한다.
 *  2. 해당 전공이 존재하는 전공인지 확인해야한다.
 *
 */
public class UserRegisterServiceTest extends UserTest{

	UserRepository fakeUserRepository;
	UserRegisterService userRegisterService;
	MajorRepository majorRepository;
	
	@BeforeEach
	void setUp() {
		fakeUserRepository = new FakeUserRepository();
		majorRepository = new FakeMajorRepository();
		majorRepository.save(new Major("컴퓨터 공학"));
	    userRegisterService = new UserRegisterService(new RegisterUserValiator(), fakeUserRepository, majorRepository, new BCryptPasswordEncoder());
	}
	
	@Test
	@DisplayName("사용자 등록 성공 케이스")
	public void register_1() {
		RegisterUser registerUser = createUser("wodyd202@naver.com","testestestes","컴퓨터 공학");
		userRegisterService.register(registerUser);
		Optional<User> findByEmail = fakeUserRepository.findByEmail(new Email("wodyd202@naver.com"));
		assertTrue(findByEmail.isPresent());
	}
	
	@Test
	@DisplayName("비어있는 값이 있을 경우")
	public void register_2() {
		assertThrows(InvalidUserException.class, ()->{
			RegisterUser registerUser = createUser("","testestestes","컴퓨터 공학");
			userRegisterService.register(registerUser);
		});
		assertThrows(InvalidUserException.class, ()->{
			RegisterUser registerUser = createUser("wodyd202@naver.com","","컴퓨터 공학");
			userRegisterService.register(registerUser);
		});
		assertThrows(InvalidUserException.class, ()->{
			RegisterUser registerUser = createUser("wodyd202@naver.com","flkdsjfdslkj","");
			userRegisterService.register(registerUser);
		});
	}
	
	@Test
	@DisplayName("중복된 이메일이 존재하는 경우")
	public void register_3() {
		RegisterUser registerUser = createUser("wodyd202@naver.com","testestestes","컴퓨터 공학");
		userRegisterService.register(registerUser);
		assertThrows(DupUserException.class, ()->{
			userRegisterService.register(registerUser);
		});
	}
	
	@Test
	@DisplayName("존재하지 않는 전공을 입력했을 때")
	public void register_4() {
		RegisterUser registerUser = createUser("wodyd202@naver.com","testestestes","없는 전공");
		assertThrows(InvalidUserException.class, ()->{
			userRegisterService.register(registerUser);
		});
	}
}
