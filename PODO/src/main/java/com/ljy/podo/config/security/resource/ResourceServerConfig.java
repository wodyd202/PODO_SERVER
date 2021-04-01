package com.ljy.podo.config.security.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers()
			.frameOptions().disable()
			.and()
			.formLogin().disable();
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/api/v1/user").permitAll()
		.antMatchers(HttpMethod.GET,"/api/v1/user/is-dup").permitAll()
		.antMatchers(HttpMethod.POST,"/api/v1/user").permitAll()
		.antMatchers(HttpMethod.PUT,"/api/v1/user").authenticated()

		.antMatchers(HttpMethod.GET,"/api/v1/major").permitAll()
		
		.antMatchers(HttpMethod.GET,"/api/v1/portfolio").permitAll()
		.antMatchers(HttpMethod.POST,"/api/v1/portfolio").authenticated()
		.antMatchers(HttpMethod.POST,"/api/v1/portfolio/fileUpload").authenticated()
		.antMatchers(HttpMethod.PUT,"/api/v1/portfolio").authenticated()
		.antMatchers(HttpMethod.DELETE,"/api/v1/portfolio").authenticated()
		
		.antMatchers(HttpMethod.GET,"/api/v1/portfolio/is-temporary").authenticated()
		.antMatchers(HttpMethod.GET,"/api/v1/portfolio/is-today").permitAll()
		.antMatchers(HttpMethod.GET,"/api/v1/portfolio").permitAll()

		.antMatchers(HttpMethod.GET,"/api/v1/attention").permitAll()
		.antMatchers(HttpMethod.POST,"/api/v1/attention").authenticated()
		.antMatchers(HttpMethod.PUT,"/api/v1/attention").authenticated()
		.antMatchers(HttpMethod.DELETE,"/api/v1/attention").authenticated()
		.and()
		.exceptionHandling()
		.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
}
