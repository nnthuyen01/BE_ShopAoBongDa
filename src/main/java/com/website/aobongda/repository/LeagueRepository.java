package com.website.aobongda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.website.aobongda.model.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
	@Query("SELECT l FROM League as l WHERE l.name LIKE ?1")
	Boolean findByName(String name);
}
