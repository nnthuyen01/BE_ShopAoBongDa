package com.website.aobongda.dto;

import com.website.aobongda.model.CartID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
	private CartID cartID;
	private int quantity;
}
