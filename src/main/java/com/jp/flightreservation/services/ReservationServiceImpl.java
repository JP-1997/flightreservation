package com.jp.flightreservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.flightreservation.controllers.dto.ReservationRequest;
import com.jp.flightreservation.entities.Flight;
import com.jp.flightreservation.entities.Passenger;
import com.jp.flightreservation.entities.Reservation;
import com.jp.flightreservation.repos.FlightRepository;
import com.jp.flightreservation.repos.PassengerRepository;
import com.jp.flightreservation.repos.ReservationRepository;
import com.jp.flightreservation.util.EmailUtil;
import com.jp.flightreservation.util.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	PDFGenerator pdfGenerator;

	@Autowired
	EmailUtil emailUtil;

	@Override
	public Reservation bookFlight(ReservationRequest request) {

		// Make payment

		Long flightId = request.getFlightId();
		Flight flight = flightRepository.findById(flightId).get();
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setPhone(request.getPassengerPhone());
		passenger.setEmail(request.getPassengerEmail());
		Passenger savedPassenger = passengerRepository.save(passenger);
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		Reservation savedReservation = reservationRepository.save(reservation);

		String filePath = "X:\\projectoutput\\reservations" + savedReservation.getId() + ".pdf";
		pdfGenerator.generateItinerary(savedReservation, filePath);
		emailUtil.sendItinerary(passenger.getEmail(), filePath);
		return savedReservation;
	}

}
