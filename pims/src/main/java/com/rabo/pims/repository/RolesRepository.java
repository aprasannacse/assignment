package com.rabo.pims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rabo.pims.entity.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {

}
