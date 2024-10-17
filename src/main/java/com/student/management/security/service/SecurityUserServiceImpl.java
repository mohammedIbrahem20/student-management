package com.student.management.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.student.management.model.Student;
import com.student.management.security.jwt.JwtUser;
import com.student.management.security.jwt.JwtUserFactory;
import com.student.management.student.service.StudentService;

@Service("securityUserService")
public class SecurityUserServiceImpl implements SecurityUserService {
	@Autowired
	private StudentService  userService;


	@Autowired
	private SessionRegistry sessionRegistry;

	@Override
	public UserDetails getUserByUserName(String username) {
		Student user = userService.getStudentByUsername(username);
		if (user != null) {

			JwtUser jUser= JwtUserFactory.create(user);
			sessionRegistry.getAllPrincipals().add(jUser);
			return jUser;
			
		}
		return null;
	}

}
