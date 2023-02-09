package com.website.aobongda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.website.aobongda.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM Product as p WHERE p.name LIKE ?1")
	Product findByName(String name);
	
	@Query(value = "Select * from product where id_club = :id_club", nativeQuery = true)
	public List<Product> getProductByIdClub(@Param("id_club") Long id);
	
	@Query(value = "select p.id, p.description, p.image, p.name, p.price, p.status, p.id_club "
			+ "from (product as p join club as c "
			+ "on p.id_club = c.id) join brand as b on b.id = c.brand_id "
			+ "where p.name like %:keyword% or c.name like %:keyword% or b.name like %:keyword%", nativeQuery = true)
	public List<Product> findByNameOrClubOrBrand(@Param("keyword") String keyword);
	
	@Query(value = "Select * from product limit 8", nativeQuery = true)
	public List<Product> getProductByTop();
}
