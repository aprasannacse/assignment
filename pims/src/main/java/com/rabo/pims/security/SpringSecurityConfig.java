package com.rabo.pims.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${spring.h2.console.path}") // Get H2 Console path from application property file
	private String h2ConsolePath;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	@Override
	protected void configure (HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		.antMatchers(h2ConsolePath+"/**").permitAll()
		.anyRequest().authenticated()
		.and().httpBasic().authenticationEntryPoint(entryPoint())
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().headers().frameOptions().sameOrigin();
	}
	
	@Bean
	public AuthenticationEntryPoint entryPoint() {
		return new BasicAuthenticationEntryPoint() {
			@Override
			public void afterPropertiesSet(){
				setRealmName("REALNAME");
				super.afterPropertiesSet();
			}
		};
	
	}
	
}
