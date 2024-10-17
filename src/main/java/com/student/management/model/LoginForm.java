package com.student.management.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginForm {
	@NotBlank
	@Size(min = 3, max = 60)
	private String username;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

}