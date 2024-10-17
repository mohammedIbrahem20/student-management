package com.student.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name = "Student")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	@NotBlank
	@Column(name="username")
	private String username;
	
	@NotBlank
	private String password;
	
	@NotNull
	@NotBlank
	@Email
	private String email;
	
}
