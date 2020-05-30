package com.jp.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jp.flightreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
