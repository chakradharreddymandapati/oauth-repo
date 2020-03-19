package com.example.demo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FrameworkEndpoint
public class CreateToken {
	@Resource(name = "defaultTokenService")
	ConsumerTokenServices tokenservice;

	@RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
	@ResponseBody
	private Boolean revokeToken(HttpServletRequest header) {
		String authentication = header.getHeader("x-requested-with");
		if (authentication != null && authentication.contains("Bearer") || authentication.contains("bearer")) {
			String tokenid = authentication.substring("bearer".length() + 1);
			return tokenservice.revokeToken(tokenid);
		}
		return false;
	}

}
