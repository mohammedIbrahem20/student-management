package com.student.management.security.jwt;

import com.student.management.model.Student;


public final class JwtUserFactory {

    private JwtUserFactory() {
    }

	public static JwtUser create(Student  user) {
    	return new JwtUser
    	(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
       
    
    }
    
}
