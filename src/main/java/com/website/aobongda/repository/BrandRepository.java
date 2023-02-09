package com.website.aobongda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.website.aobongda.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
	 @Query("SELECT b FROM Brand as b WHERE b.name LIKE ?1")
	Boolean findByName(String name);
}
