package com.example.demo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

@EnableResourceServer
@Configuration
public class ResourceConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	Environment ev;

	@Bean
	private LdapAuthenticationProvider authProvider() {
		LdapAuthenticationProvider provider = new LdapAuthenticationProvider(customBindAuthenticator(),
				LdapAuthoritiesPopulator());
		System.out.println("ok");
		System.out.println("ok12");
		return provider;
	}

	@Bean
	private LdapAuthoritiesPopulator LdapAuthoritiesPopulator() {
		DefaultLdapAuthoritiesPopulator df = new DefaultLdapAuthoritiesPopulator(contextSource(),
				ev.getProperty("ldapGroupOu"));
		return df;
	}

	@Bean
	private CustomBindAuthenticator customBindAuthenticator() {
		CustomBindAuthenticator bind = new CustomBindAuthenticator(contextSource());
		bind.setUserDnPatterns(new String[] { ev.getProperty("ldapUserOu") });
		return bind;
	}

	@Bean
	private DefaultSpringSecurityContextSource contextSource() {

		return new DefaultSpringSecurityContextSource(Arrays.asList(ev.getProperty("ldapUrl")),
				ev.getProperty("ldapBasedn"));
	}
	

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(HttpSecurity httpSecurity) {
		try {
			httpSecurity.cors().and().authorizeRequests()
					.antMatchers(HttpMethod.OPTIONS, "/oauth/token", "/validateUser").permitAll().anyRequest()
					.authenticated();
			httpSecurity.csrf().disable().authorizeRequests();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider()).ldapAuthentication().
		userDnPatterns(ev.getProperty("ldapUserOu"))
        .groupSearchFilter(ev.getProperty("groupSearchFilter"))
        .groupSearchBase(ev.getProperty("ldapGroupOu"))
        .contextSource(contextSource());
	}

}
