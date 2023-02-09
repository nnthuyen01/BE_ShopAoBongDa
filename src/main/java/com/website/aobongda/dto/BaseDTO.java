package com.website.aobongda.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Data
public abstract class BaseDTO {
	protected long id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createdDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date modifiedDate;
	protected String createdBy;
	protected String modifiedBy;

}
