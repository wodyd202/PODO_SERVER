package com.ljy.podo.user.service.loadUser.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ljy.podo.user.Email;
import com.ljy.podo.user.aggregate.User;
import com.ljy.podo.user.aggregate.exception.InvalidUserException;
import com.ljy.podo.user.infrastructure.SimpleUserRepository;
import com.ljy.podo.user.service.loadUser.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserLoadService implements UserDetailsService{
		
	private final SimpleUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(!isValidEmail(username)) {
			throw new InvalidUserException("이메일 형식이 올바르지 않습니다. 다시 입력해주세요.", "email");
		}
		User loginUser = userRepository
				.findByEmail(new Email(username))
				.orElseThrow(()->new UsernameNotFoundException("아이디 혹은 비밀번호가 일치하지 않습니다."));
		return new UserPrincipal(loginUser);
	}
	public boolean isValidEmail(String email) {
		boolean err = false;
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		if (m.matches()) {
			err = true;
		}
		return err;
	}
}
