package com.rabo.pims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rabo.pims.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("FROM User WHERE name=:name")
	User  findByName(@Param("name") String name);
}
