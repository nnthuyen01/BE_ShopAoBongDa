package com.website.aobongda.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VoucherResponse {
	Long id;
	String code;
	Long price;
	int status;
}
