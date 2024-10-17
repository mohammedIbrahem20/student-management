package com.student.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.student.management.model.CourseRegistration;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    List<CourseRegistration> findByStudentId(Long studentId);
    CourseRegistration findByStudentIdAndCourseId(Long studentId, Long courseId);
    
    List<CourseRegistration> findByCourseId(Long courseId);
}
