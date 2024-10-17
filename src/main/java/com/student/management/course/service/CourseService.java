package com.student.management.course.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.student.management.model.Course;
import com.student.management.model.Student;

import net.sf.jasperreports.engine.JRException;

public interface CourseService {

	public List<Course> getAllCourses();

	public ResponseEntity<String> registerUserToCourse(Long courseId, Long userId);

	public boolean cancelCourseRegistration(Student user, Long courseId);

	public byte[] getCourseScheduleAsPDF(Long courseId) throws JRException;
}
