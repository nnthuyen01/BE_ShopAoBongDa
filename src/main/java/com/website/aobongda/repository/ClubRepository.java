package com.website.aobongda.repository;

import java.util.List;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.website.aobongda.model.Club;
import com.website.aobongda.model.League;
import com.website.aobongda.model.Product;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
	@Query("SELECT c FROM Club as c WHERE c.name LIKE ?1")
	Club findByName(String name);
	
	List<Club> findByLeague(League league);
}
