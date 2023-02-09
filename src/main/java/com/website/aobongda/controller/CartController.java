package com.website.aobongda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.Utils.Utils;
import com.website.aobongda.dto.CartResp;
import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.model.Cart;
import com.website.aobongda.model.CartID;
import com.website.aobongda.service.impl.ICartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {
	private final ICartService iCartService;

	@PostMapping("/cart/add")
	public ResponseEntity<?> addProduct(@RequestParam Long productId, @RequestParam int quantity) {

		iCartService.save(productId, quantity);
		return ResponseEntity.ok(new ResponseDTO(true, "Success", null));
	}

	public CartID getCartId(Long productId) {
		Long id = Utils.getIdCurrentUser();
		CartID cartID = new CartID(productId, id);
		return cartID;
	}

	@PutMapping("/cart/update")
	public ResponseEntity<?> updateQuantity(@RequestParam Long productId, @RequestParam int quantity) {

		CartID cartID = getCartId(productId);
		Cart updatedCart = iCartService.updateCart(cartID, quantity);
		if (quantity < 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseDTO(false, "Quantity Must Greater Than 0", null));
		}
		if (quantity > 99) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseDTO(false, "Quantity Must Lower Than 100", null));
		} else {
			if (updatedCart != null)
				return ResponseEntity.ok(new ResponseDTO(true, "Success", updatedCart));
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(false, "Cart Product ID = "
						+ cartID.getProductId() + " with User ID = " + cartID.getUserId() + " not exits ", null));
		}
	}

	@GetMapping("/cart")
	public ResponseEntity<?> getCart() {
		Long userId = Utils.getIdCurrentUser();
		List<CartResp> cartResps = iCartService.view(userId);
		if (cartResps != null) {
			return ResponseEntity.ok(new ResponseDTO(true, "Success", cartResps));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false, "No Item!", null));
		}
	}

	@DeleteMapping("/cart/remove")
	public ResponseEntity<?> removeCartItem(@RequestParam Long productId) {
		CartID cartID = getCartId(productId);
		Integer check = iCartService.deleteCart(cartID);
		if (check >= 1) {
			return ResponseEntity.ok(new ResponseDTO(true, check.toString() + " rows affected", check));
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(false, "Cart Product ID = "
					+ cartID.getProductId() + " with User ID = " + cartID.getUserId() + " not exits ", cartID));
	}

	@DeleteMapping("/cart")
	public ResponseEntity<?> deleteAllCartUser() {
		iCartService.deleteAllCartUser();
		return ResponseEntity.ok(new ResponseDTO(true, "Success", null));
	}
}
