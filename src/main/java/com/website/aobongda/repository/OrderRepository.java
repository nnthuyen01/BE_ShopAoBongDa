package com.website.aobongda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.website.aobongda.dto.TotalPriceByStatus;
import com.website.aobongda.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Order[] getOrderById(Order order);
	
	@Query(value = "SELECT o.status AS status, sum(o.total_price) AS totalPrice "
			+ "FROM orders AS o group by o.status", nativeQuery = true)
	public List<TotalPriceByStatus> totalByStatus();
	
	@Query(value = "SELECT * FROM orders where status = :status", nativeQuery = true)
	public List<Order> getOrdersByStatus(@Param("status") int status);
	
	@Query(value = "SELECT * FROM orders where id_user = :id_user", nativeQuery = true)
	public List<Order> findByIdUser(@Param("id_user") Long idUser);
}
