package com.website.aobongda.service.impl;

import java.util.List;

import com.website.aobongda.dto.CartResp;
import com.website.aobongda.model.Cart;
import com.website.aobongda.model.CartID;

public interface ICartService {
   
	void save(Long productId, int quantity);
    Cart updateCart(CartID cartID, int quantity);
    List<CartResp> view(Long userID);
    Integer deleteCart(CartID cartID);
    void deleteAllCartUser();
}
