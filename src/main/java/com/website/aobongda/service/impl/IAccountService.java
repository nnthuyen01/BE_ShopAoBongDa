package com.website.aobongda.service.impl;

import com.website.aobongda.dto.UserDTO;
import com.website.aobongda.payload.response.DataResponse;

public interface IAccountService {
	DataResponse<UserDTO> getAllAccountUser();
	DataResponse<UserDTO> getAccountUserById(Long id);
	DataResponse<?> editAccountUser(Long id);
	DataResponse<?> blockAccountUser(Long id);
}
