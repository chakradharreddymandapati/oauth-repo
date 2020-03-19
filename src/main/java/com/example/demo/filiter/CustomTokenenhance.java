package com.example.demo.filiter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class CustomTokenenhance implements TokenEnhancer{

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		final Map<String, Object> additionalInformation=new HashMap<String, Object>();
		additionalInformation.put("customInfo", "some_stuff_here");
		additionalInformation.put("authorities", user.getAuthorities());
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInformation);
		return accessToken;
	}

}
