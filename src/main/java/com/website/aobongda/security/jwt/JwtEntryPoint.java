package com.website.aobongda.security.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint{
	private static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error -> Message {}", authException.getMessage());
		String exceptionMessage = authException.getMessage();
		String message;
		
		if (exceptionMessage.contains("Unable to read JSON value")) {
			message = "Invalid JWT token";
		}
		else if (exceptionMessage.contains("JWT expired")) {
			message = "JWT token expired";
		}
		else {
			switch (exceptionMessage) {
			case "Bad credentials": {
				message = "Username or password is wrong";
				break;
			}
			case "Full authentication is required to access this resource":
				message = "You need authenticate first to access this api";
				break;
			default:
				message = "General error";
			}
		}
		
		response.resetBuffer();
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setHeader("Content-Type", "application/json");
		response.getOutputStream().write(String.format(
				"{\"message\":\"%s\",\"detail\":%s}", 
				message,
				new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(new AuthExceptionInfo(authException))).getBytes(StandardCharsets.UTF_8));
		response.flushBuffer();
	}
	
	@Getter @Setter
	private class AuthExceptionInfo {
		private String message;
		private Throwable[] suppressed;
		private String localizedMessage;

		public AuthExceptionInfo(AuthenticationException authException) {
			this.message = authException.getMessage();
			this.suppressed = authException.getSuppressed();
			this.localizedMessage = authException.getLocalizedMessage();
		}
	}

}
