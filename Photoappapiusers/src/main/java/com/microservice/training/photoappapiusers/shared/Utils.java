package com.microservice.training.photoappapiusers.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	private final Random random = new SecureRandom();
	private final String Alphabets = "abcdefghijklmanopqrstuvwxyz123456789";

	public String generateUserId(int length) {

		return generateRandomString(length);
	}

	public String generateAddressId(int length) {

		return generateRandomString(length);
	}

	private String generateRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			returnValue.append(Alphabets.charAt(random.nextInt(Alphabets.length())));
		}
		return new String(returnValue);
	}
	
	private String generateEncryptedPassword(int length) {
		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			returnValue.append(Alphabets.charAt(random.nextInt(Alphabets.length())));
		}
		return new String(returnValue);
	}

}
