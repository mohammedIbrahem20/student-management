package com.student.management.student.service;

import org.springframework.http.ResponseEntity;

import com.student.management.model.Student;

public interface StudentService {

	ResponseEntity<String> registration(Student user);
	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
	Student getStudent(Long id);
	Student getStudentByUsername(String username);
}
