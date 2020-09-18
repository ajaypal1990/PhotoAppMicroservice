package com.microservice.training.photoappapiusers.service;

import java.nio.file.attribute.UserDefinedFileAttributeView;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.microservice.training.photoappapiusers.shared.UserDto;

public interface UserService extends UserDetailsService{

	UserDto createUser(UserDto userDto);
	
	UserDto getUserDetailsByEmail(String email);

}
