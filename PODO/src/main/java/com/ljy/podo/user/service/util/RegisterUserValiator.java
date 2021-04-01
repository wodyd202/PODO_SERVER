package com.ljy.podo.user.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ljy.podo.common.Validator;
import com.ljy.podo.user.aggregate.User.UserType;
import com.ljy.podo.user.aggregate.exception.InvalidUserException;
import com.ljy.podo.user.service.registerUser.RegisterUser;

public class RegisterUserValiator implements Validator<RegisterUser> {

	@Override
	public void validate(RegisterUser obj) {
		String email = obj.getEmail();
		String password = obj.getPassword();
		String major = obj.getMajor();
		UserType userType = obj.getUserType();

		verfyNotNullUserType(userType);
		verfyNotEmptyStringValue(email, "이메일을 입력해주세요", "email");
		if(!isValidEmail(email)) {
			throw new InvalidUserException("이메일 형식이 올바르지 않습니다. 다시 입력해주세요.", "email");
		}
		verfyNotEmptyStringValue(password, "비밀번호를 입력해주세요", "password");
		verfyNotEmptyStringValue(major, "전공을 입력해주세요", "major");
	}

	private void verfyNotNullUserType(UserType userType) {
		if (userType == null) {
			throw new InvalidUserException("사용자 구분을 입력해주세요", "userType");
		}
	}

	private void verfyNotEmptyStringValue(String value, String msg, String field) {
		if (value == null || value.isEmpty()) {
			throw new InvalidUserException(msg, field);
		}
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
