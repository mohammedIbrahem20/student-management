package com.student.management.student.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.management.model.Student;
import com.student.management.student.service.StudentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class StudentController {

	@Autowired
	StudentService userService;

	@PostMapping
	public ResponseEntity<String> saveUser(@Valid @RequestBody Student user) {

		userService.registration(user);
		return new ResponseEntity<>("USER_ADDED_SUCCESSFULLY", HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Student> getUserById(@PathVariable Long id) throws Exception {
		Student user = userService.getStudent(id);
		if (user == null) {
			throw new Exception("User doesn´t exist");
		}

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
