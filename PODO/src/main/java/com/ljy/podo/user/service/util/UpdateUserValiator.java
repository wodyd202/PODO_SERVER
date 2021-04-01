package com.ljy.podo.user.service.util;

import com.ljy.podo.common.Validator;
import com.ljy.podo.user.aggregate.exception.InvalidUserException;
import com.ljy.podo.user.service.updateUser.UpdateUser;

public class UpdateUserValiator implements Validator<UpdateUser> {

	@Override
	public void validate(UpdateUser obj) {
		String password = obj.getPassword();
		verfyNotEmptyStringValue(password, "비밀번호를 입력해주세요", "password");
	}

	
	private void verfyNotEmptyStringValue(String value, String msg, String field) {
		if (value == null || value.isEmpty()) {
			throw new InvalidUserException(msg, field);
		}
	}

}
