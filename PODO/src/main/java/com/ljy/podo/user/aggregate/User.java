package com.ljy.podo.user.aggregate;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ljy.podo.user.Email;
import com.ljy.podo.user.Major;
import com.ljy.podo.user.Password;
import com.ljy.podo.user.Profile;
import com.ljy.podo.user.UserState;
import com.ljy.podo.user.service.updateUser.UpdateUser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "email"))
	private Email email;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "password"))
	private Password password;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(nullable = false, name = "major"))
	private Major major;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "profile"))
	private Profile profile;

	@Enumerated(EnumType.STRING)
	private UserType userType;

	@Enumerated(EnumType.STRING)
	private UserState state;

	public void encodePassword(PasswordEncoder encoder) {
		this.password = new Password(encoder.encode(this.password.toString()));
	}
	
	public User(Email email, Password password, Major major, UserType userType) {
		this.email = email;
		this.password = password;
		this.major = major;
		this.state = UserState.CAN_BE_USED;
		this.userType = userType;
	}

	public enum UserType {
		STUDENT, COMPANY
	}

	public void update(UpdateUser obj) {
		this.password = new Password(obj.getPassword());
		if (obj.getProfile() != null && !obj.getProfile().isEmpty()) {
			this.profile = new Profile(obj.getProfile());
		}
	}
}
