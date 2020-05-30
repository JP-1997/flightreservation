package com.jp.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jp.flightreservation.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
