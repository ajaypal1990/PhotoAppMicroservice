package com.microservice.training.photoappapiusers.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {

	@NotNull(message="First Name cannot be Empty")
	@Size(min=2,message="First Name must be of 2 character long")
	private String firstName;
	@NotNull(message="First Name cannot be Empty")
	@Size(min=2,message="First Name must be of 2 character long")
	private String lastName;
	@NotNull(message="Password cannot be Empty")
	@Size(min=2,max=16,message="Password must be of 2 character long")
	private String password;
	@NotNull(message="Emaill cannot be Empty")
	@Email
	private String email;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
