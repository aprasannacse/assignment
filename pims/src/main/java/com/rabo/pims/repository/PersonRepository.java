package com.rabo.pims.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rabo.pims.entity.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {	

	@Query("SELECT p FROM PersonEntity p where UPPER(p.firstName) = UPPER(?1) and UPPER(p.lastName) = UPPER(?2)")
	Optional<List<PersonEntity>> findByfirstAndlastName(String first_name,String lastName);

	@Query("SELECT p FROM PersonEntity p where UPPER(p.firstName) = UPPER(?1) or UPPER(p.lastName) = UPPER(?2) ")
	Optional<List<PersonEntity>> findByName(String first_name,String lastName);

}
