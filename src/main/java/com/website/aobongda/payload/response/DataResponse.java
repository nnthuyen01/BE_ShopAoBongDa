package com.website.aobongda.payload.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse<T> extends BaseResponse {
	private T data;
	private List<T> datas = new ArrayList<>();

	public DataResponse(Boolean success, String message, T data) {
		super(success, message);
		this.data = data;
	}

	public DataResponse(T data) {
		super(true, "");
		this.data = data;
	}

	public DataResponse() {
		super();
	}
}
