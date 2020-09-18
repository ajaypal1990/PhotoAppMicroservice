package com.microservice.training.photoappapiusers.service.impl;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservice.training.photoappapiusers.data.UserEntity;
import com.microservice.training.photoappapiusers.service.UserService;
import com.microservice.training.photoappapiusers.shared.UserDto;
import com.microservice.training.photoappapiusers.shared.UserRepository;
import com.microservice.training.photoappapiusers.shared.Utils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private Utils util;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		ModelMapper mapper=new ModelMapper();
		String publicUserId=util.generateUserId(5);
		userDto.setUserId(publicUserId);
		userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		UserEntity entity=new UserEntity();
		//UserEntity entity=mapper.map(userDto, UserEntity.class);
		
		BeanUtils.copyProperties(userDto, entity);
		
		repository.save(entity);
		
		UserDto returnValue=mapper.map(entity, UserDto.class);
		
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity entity=repository.findByEmail(username);
		if(entity==null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(entity.getEmail(), entity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		
		UserEntity entity=repository.findByEmail(email);
		if(entity==null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new ModelMapper().map(entity, UserDto.class);
	}

}
