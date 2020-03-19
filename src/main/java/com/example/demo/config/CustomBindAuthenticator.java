package com.example.demo.config;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;

public class CustomBindAuthenticator extends BindAuthenticator {

	public CustomBindAuthenticator(DefaultSpringSecurityContextSource contextSource) {
		super(contextSource);
	}
	@Override
	public void handleBindException(String userDn, String username, Throwable cause) {
		super.handleBindException(userDn,username,cause);
		if(cause.getMessage().contains("account was permanently locked")) {
		     throw new LockedException(messages.getMessage(
 	                "LdapAuthenticationProvider.locked", "User account is locked"), cause);
 	    }
 	    //second example: handle password lifetime reached
 	    if (cause.getMessage().contains("password end time reached"))
 	    {
 	        throw new AccountExpiredException(messages.getMessage(
 	                "LdapAuthenticationProvider.expired", "User account has expired"),
 	                cause);
 	    }
	}

}
