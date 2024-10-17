package com.student.management.security.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.student.management.security.jwt.JwtUser;

public interface SecurityUserService {
	UserDetails getUserByUserName(String username);
}
