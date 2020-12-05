package com.rabo.pims.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rabo.pims.entity.PetEntity;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Integer>{
	@Query("SELECT p FROM PetEntity p where personEntity.person_id = ?1 ")
	Optional<List<PetEntity>> findByPersonID(int personId);

}
