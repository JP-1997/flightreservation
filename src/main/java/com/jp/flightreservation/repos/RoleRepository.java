package com.jp.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jp.flightreservation.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
