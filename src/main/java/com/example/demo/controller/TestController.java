package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
@Autowired
private ConsumerTokenServices defaultTokenService;
@RequestMapping("/valieduser")
private Principal principle(Principal user) {
	return user;
}
@RequestMapping(method = RequestMethod.DELETE,value = "/oauth/token/revoke")
@ResponseBody
private boolean create(@RequestParam("token") String value) {
	return defaultTokenService.revokeToken(value);
}
}
