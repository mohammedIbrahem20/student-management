package com.student.management.security.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.student.management.model.Student;

public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 15225762647540176L;
	private final Long id;
	private final String username;
	@JsonIgnore
	private final String password;
	private final String email;

	public Student getUser() {
		Student user = new Student();
		user.setId(getId());
		user.setUsername(getUsername());
		return user;
	}

	public JwtUser(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities, boolean enabled) {

		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public JwtUser(Long id, String username, String email, String password) {

		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;

	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getEmail() {
		return email;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
