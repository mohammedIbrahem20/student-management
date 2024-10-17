package com.student.management.course.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.student.management.model.Course;
import com.student.management.model.CourseRegistration;
import com.student.management.model.Student;
import com.student.management.repository.CourseRepository;
import com.student.management.repository.RegistrationRepository;
import com.student.management.student.service.StudentService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private RegistrationRepository registrationRepository;
	@Autowired
	private StudentService studentService;

	@Override
	@Cacheable(value = "courses")
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	public ResponseEntity<String> registerUserToCourse(Long courseId, Long userId) {
		Student student = studentService.getStudent(userId);

		if (student == null) {
			return ResponseEntity.status(404).body("User not found");
		}

		// Check if the course exists
		Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

		// Check if the user is already registered
		CourseRegistration existingRegistration = registrationRepository.findByStudentIdAndCourseId(student.getId(),
				courseId);
		if (existingRegistration != null) {
			throw new RuntimeException("User is already registered for this course");
		}

		CourseRegistration registration = new CourseRegistration();
		registration.setStudent(student);
		registration.setCourse(course);

		registrationRepository.save(registration);
		return ResponseEntity.ok("Successfully registered to the course.");
	}

	@Override
	public boolean cancelCourseRegistration(Student user, Long courseId) {
		CourseRegistration registration = registrationRepository.findByStudentIdAndCourseId(user.getId(), courseId);

		if (registration != null) {
			registrationRepository.delete(registration);
			return true; 
		}

		return false; 
	}
	
	public byte[] getCourseScheduleAsPDF(Long courseId) throws JRException {
//	    Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
	    List<CourseRegistration> schedualCourses = getCoursesRegistrationByCourseId(courseId);

	    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(schedualCourses);

	    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("schedule_template.jrxml");
	    if (inputStream == null) {
	        throw new RuntimeException("Template file not found");
	    }

	    JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

	    HashMap<String, Object> parameters = new HashMap<>();
	    for(CourseRegistration schedualCourse : schedualCourses) {
	    	parameters.put("courseName" + schedualCourse.getId(), schedualCourse.getCourse().getName());
	    	parameters.put("description" + schedualCourse.getId(), schedualCourse.getCourse().getDescription());
	    	parameters.put("instructor" + schedualCourse.getId(), schedualCourse.getCourse().getInstructor());
	    	parameters.put("duration" + schedualCourse.getId(), schedualCourse.getCourse().getDuration());
	    	parameters.put("studentName" + schedualCourse.getId(), schedualCourse.getStudent().getUsername());
	    }

	    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

	    return outputStream.toByteArray();
	}
	
	@Cacheable(value = "scheduled_courses")
	private List<CourseRegistration> getCoursesRegistrationByCourseId(Long courseId){
		return registrationRepository.findByCourseId(courseId);
	}
}
