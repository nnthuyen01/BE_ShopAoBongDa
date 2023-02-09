package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.aobongda.Utils.Utils;
import com.website.aobongda.dto.CartDTO;
import com.website.aobongda.dto.CartResp;
import com.website.aobongda.model.Cart;
import com.website.aobongda.model.CartID;
import com.website.aobongda.model.Product;
import com.website.aobongda.model.User;
import com.website.aobongda.repository.CartRepository;
import com.website.aobongda.repository.ProductRepository;
import com.website.aobongda.repository.UserRepository;
import com.website.aobongda.security.userprincipal.UserPrincipal;
import com.website.aobongda.service.impl.ICartService;
import com.website.aobongda.service.impl.IProductService;
import com.website.aobongda.service.impl.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService implements ICartService {
	@Autowired
	CartRepository cartRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	IProductService productService;
	@Autowired
	IUserService userService;
	@Autowired
	ProductRepository productRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public void save(Long productId, int quantity) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Long id = user.getId();
		CartDTO cartDTO = new CartDTO(new CartID(productId, Long.valueOf(id)), quantity);
		update(cartDTO);
	}

	public void update(CartDTO cartDTO) {
		Cart updatedCart = cartRepo.findCartByUserIdAndProductId(cartDTO.getCartID().getUserId(),
				cartDTO.getCartID().getProductId());

		if (updatedCart == null) {
			updatedCart = modelMapper.map(cartDTO, Cart.class);
			cartRepo.save(updatedCart);
		} else {
			updatedCart.setQuantity(updatedCart.getQuantity() + cartDTO.getQuantity());
			cartRepo.save(updatedCart);
		}
	}

	@Override
	public Cart updateCart(CartID cartID, int quantity) {
		Cart updatedCart = cartRepo.findById(cartID).orElse(null);
		if (updatedCart != null) {
			updatedCart.setQuantity(quantity);
			cartRepo.save(updatedCart);
		}
		return updatedCart;
	}

	@Override
	public List<CartResp> view(Long userID) {
		List<Cart> cartList = cartRepo.findByCartID_UserId(userID);
		List<CartResp> cartResps = new ArrayList<>();
		if (cartList != null) {
			cartList.forEach(cart -> {
				CartResp cartResp = new CartResp();
				Product product = productRepo.getReferenceById(cart.getCartID().getProductId());
				cartResp.setProduct_Id(product.getId());
				cartResp.setQuantity(cart.getQuantity());
				cartResp.setItem(new CartResp.Items(product.getName(), product.getPrice(), product.getImage()));
				cartResps.add(cartResp);
			});
			return cartResps;
		} else {
			return null;
		}
	}

	@Override
	public Integer deleteCart(CartID cartID) {
		return cartRepo.deleteByCartID(cartID);
	}

	@Override
	public void deleteAllCartUser() {
		Long userId = Utils.getIdCurrentUser();
		cartRepo.deleteAllByCartID_UserId(userId);
	}
}
