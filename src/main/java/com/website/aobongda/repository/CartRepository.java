package com.website.aobongda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.website.aobongda.model.Cart;
import com.website.aobongda.model.CartID;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartID> {

	/*
	 * findByIdUser_id -> directive "findBy" field "CartID.user_id"
	 * findByIdProduct_id -> directive "findBy" field "CartID.product_id" Search
	 * Spring Boot Composite Key for more references
	 */
	List<Cart> findByCartID_UserId(Long user_id);

	@Query("select c from Cart c where c.cartID.userId = :userId and c.cartID.productId = :productId")
	Cart findCartByUserIdAndProductId(Long userId, Long productId);

	@Modifying
	@Transactional
	Integer deleteByCartID(CartID id);

	void deleteAllByCartID_UserId(Long userId);
}
