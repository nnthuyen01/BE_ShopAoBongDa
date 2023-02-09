package com.website.aobongda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
	private Boolean status;
	private String message;
	private Object data;
}
