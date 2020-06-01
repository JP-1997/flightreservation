package com.jp.flightreservation.services;

import com.jp.flightreservation.controllers.dto.ReservationRequest;
import com.jp.flightreservation.entities.Reservation;

public interface ReservationService {

	public Reservation bookFlight(ReservationRequest request);
}
