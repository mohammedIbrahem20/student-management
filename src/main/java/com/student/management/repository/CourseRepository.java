package com.student.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.student.management.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
   
}
