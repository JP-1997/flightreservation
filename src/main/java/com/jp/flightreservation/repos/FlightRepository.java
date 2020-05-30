package com.jp.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jp.flightreservation.entities.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

}
