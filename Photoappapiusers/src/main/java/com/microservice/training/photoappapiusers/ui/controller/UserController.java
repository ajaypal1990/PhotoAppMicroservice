package com.microservice.training.photoappapiusers.ui.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.training.photoappapiusers.service.UserService;
import com.microservice.training.photoappapiusers.shared.UserDto;
import com.microservice.training.photoappapiusers.ui.model.CreateUserRequestModel;
import com.microservice.training.photoappapiusers.ui.model.CreateUserResponseModel;
import com.microservice.training.photoappapiusers.ui.model.UserResponceModel;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private Environment env;
	
	@Autowired
	private UserService usersService;
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/status/check")
	public String status() {
		return "Working on port " + env.getProperty("local.server.port") + ", with token = "
				+ env.getProperty("token.secret");
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			)
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails)
	{
		ModelMapper modelMapper = new ModelMapper(); 
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		UserDto createdUser = usersService.createUser(userDto);
		
		CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}
	
	
	@GetMapping(value="/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserResponceModel> getUser(@PathVariable("userId") String userId){
		
		UserDto userDto=usersService.getUserByUserId(userId);
		
		UserResponceModel returnValue=new ModelMapper().map(userDto, UserResponceModel.class);
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
		
	}

}
