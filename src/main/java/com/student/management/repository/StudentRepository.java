package com.student.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.student.management.model.Student;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<Student, Long> {

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
	Student findByUsername(String User);
}