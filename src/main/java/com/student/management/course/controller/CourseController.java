package com.student.management.course.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.management.course.service.CourseServiceImpl;
import com.student.management.model.Course;
import com.student.management.model.Student;
import com.student.management.security.jwt.JwtProvider;
import com.student.management.student.service.StudentService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

	@Autowired
	private CourseServiceImpl courseService;

	@Autowired
	private StudentService userService;

	@Autowired
	private JwtProvider jwtUtil;

	@GetMapping
	public List<Course> viewCourses() {
		return courseService.getAllCourses();
	}

	@PostMapping("/register/{courseId}/{userId}")
	public ResponseEntity<String> registerToCourse(@PathVariable Long courseId, @PathVariable Long userId) {
		return courseService.registerUserToCourse(courseId, userId);

	}

	@DeleteMapping("/{courseId}/cancel")
	public ResponseEntity<String> cancelCourseRegistration(@PathVariable Long courseId,
			@RequestHeader("Authorization") String token) {
		String username = jwtUtil.getUserNameFromJwtToken(token.substring(7));
		Student user = userService.getStudentByUsername(username);

		if (user == null) {
			return ResponseEntity.status(404).body("User not found");
		}

		boolean canceled = courseService.cancelCourseRegistration(user, courseId);

		if (canceled) {
			return ResponseEntity.ok("Course registration canceled successfully.");
		} else {
			return ResponseEntity.status(404).body("No registration found for this course.");
		}
	}

	@GetMapping("/schedule/{courseId}")
	public ResponseEntity<?> getCourseSchedule(@PathVariable Long courseId) {
		try {
			byte[] pdfContent = courseService.getCourseScheduleAsPDF(courseId);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=schedule.pdf");
			return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
