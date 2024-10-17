package com.student.management.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.management.model.Student;
import com.student.management.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	PasswordEncoder encoder;

	@Override
	public ResponseEntity<String> registration(Student user) {
		user.setPassword(encoder.encode(user.getPassword()));

		if (existsByUsername(user.getUsername())) {
			return new ResponseEntity<>("USERNAME_IS_ALREADY_TAKEN", HttpStatus.BAD_REQUEST);
		}
		if (existsByEmail(user.getEmail())) {
			return new ResponseEntity<>("EMAIL_IS_ALREADY_INUSE", HttpStatus.BAD_REQUEST);
		}
		
		studentRepository.save(user);
		
		return new ResponseEntity<>("User saved successfully", HttpStatus.OK);
	}

	@Override
	public boolean existsByUsername(String username) {
		return studentRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return studentRepository.existsByEmail(email);
	}


	@Override
	public Student getStudent(Long id) {
		return studentRepository.findById(id).get();
	}


	@Override
	public Student getStudentByUsername(String username) {
		return studentRepository.findByUsername(username);
	}
}
