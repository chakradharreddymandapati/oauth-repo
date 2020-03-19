package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private DataSource datasource;

	@Bean
	private TokenStore tokenstore() {
		return new JdbcTokenStore(datasource);
	}

	@Bean
	public DefaultTokenServices defaulttoken() {
		DefaultTokenServices dftoken = new DefaultTokenServices();
		dftoken.setTokenStore(tokenstore());
		return dftoken;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer client) throws Exception {
		client.inMemory().withClient("DapClientId").secret("{noop}secret").authorizedGrantTypes("password")
				.scopes("resource-server-read", "resource-server-write", "read", "write").autoApprove(true);
	}

	@Autowired
	public void configure(AuthorizationServerEndpointsConfigurer end) {
		end.authenticationManager(authenticationManager).tokenStore(tokenstore());
	}
}
