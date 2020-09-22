package com.microservice.training.photoappapiusers.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.training.photoappapiusers.data.AlbumServiceClient;
import com.microservice.training.photoappapiusers.data.UserEntity;
import com.microservice.training.photoappapiusers.service.UserService;
import com.microservice.training.photoappapiusers.shared.UserDto;
import com.microservice.training.photoappapiusers.shared.UserRepository;
import com.microservice.training.photoappapiusers.shared.Utils;
import com.microservice.training.photoappapiusers.ui.model.AlbumResponseModel;

@Service
public class UserServiceImpl implements UserService {
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private Utils util;
	
	@Autowired
	private AlbumServiceClient feignClient;
	
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

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity=repository.findByUserId(userId);
		if(userEntity==null) {
			throw new UsernameNotFoundException(userId);
		}
		
		UserDto returnValue=new ModelMapper().map(userEntity, UserDto.class);
		
		/*
		 * String albumUrls=String.format(env.getProperty("albums.url"),userId);
		 * 
		 * ResponseEntity<List<AlbumResponseModel>>
		 * albumListResponce=restTemplate.exchange(albumUrls, HttpMethod.GET , null, new
		 * ParameterizedTypeReference<List<AlbumResponseModel>>() { });
		 * 
		 * List<AlbumResponseModel> albumList=albumListResponce.getBody();
		 */
		List<AlbumResponseModel> albumList = feignClient.getAlbums(userId);
		
		returnValue.setAlbumList(albumList);
		return returnValue;
	}

}
