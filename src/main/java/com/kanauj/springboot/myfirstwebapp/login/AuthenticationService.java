package com.kanauj.springboot.myfirstwebapp.login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	
	public boolean authenticate(String username, String password)
	{
		boolean isValidUsername=username.equalsIgnoreCase("kanauj");
		boolean isValidPassword=password.equalsIgnoreCase("good");
		return isValidUsername && isValidPassword;
	}

}
