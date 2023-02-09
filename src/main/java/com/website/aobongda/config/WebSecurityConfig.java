package com.website.aobongda.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.website.aobongda.security.jwt.CustomAccessDeniedHandler;
import com.website.aobongda.security.jwt.JwtEntryPoint;
import com.website.aobongda.security.jwt.JwtTokenFilter;
import com.website.aobongda.security.userprincipal.UserDetailService;


@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserDetailService userDetailService;
	@Autowired
	private JwtEntryPoint jwtEntryPoint;
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	@Bean
	public JwtTokenFilter jwtTokenFilter() {
		return new JwtTokenFilter();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.cors().and().csrf().disable()
		.authorizeRequests().antMatchers("/api/signup", "/api/login", "/api/admin/login").permitAll()
		.and()
		.authorizeRequests()
		.antMatchers("/api/admin/**", "/test").hasRole("ADMIN")
		.and()
		.authorizeRequests()
		.antMatchers("/api/user/**").hasRole("USER")
		.and()
		.authorizeRequests()
		.antMatchers("/**").permitAll()
		.anyRequest().authenticated()
		.and().exceptionHandling()
		.authenticationEntryPoint(jwtEntryPoint)
		.and().exceptionHandling()
		.accessDeniedHandler(customAccessDeniedHandler)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
