package com.website.aobongda.exception.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import com.website.aobongda.exception.ExceptionResq;
import com.website.aobongda.exception.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private ResponseEntity<?> notFound(NotFoundException e) {
		return ResponseEntity.status(404).body(new ExceptionResq(404, e.getMessage()));
	}
}
