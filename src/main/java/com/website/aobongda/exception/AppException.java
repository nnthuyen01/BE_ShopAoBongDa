package com.website.aobongda.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int code;
	private String message;
}
