package com.ljy.podo.common;

import org.springframework.transaction.annotation.Transactional;

abstract public class RegisterService<T> {
	private Validator<T> validator;

	@Transactional
	abstract protected void reigsterEntity(T obj);
	
	@Transactional
	protected void afterValidation(T obj) {
	}
	
	@Transactional
	protected void beforeValidation(T obj) {
	}
	
	@Transactional
	public void register(T obj) {
		beforeValidation(obj);
		validator.validate(obj);
		afterValidation(obj);
		reigsterEntity(obj);
	}

	public RegisterService(Validator<T> validate) {
		this.validator = validate;
	}
}
