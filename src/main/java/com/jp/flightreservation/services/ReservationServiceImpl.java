package com.jp.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jp.flightreservation.controllers.ReservationController;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);
	
	@Value("${com.jp.flightreservation.itinerary.dirpath}")
	private String ITINERARY_DIR;

	@Override
	public Reservation bookFlight(ReservationRequest request) {

		LOGGER.info("Inside bookFlight()");

		// Make payment

		Long flightId = request.getFlightId();
		LOGGER.info("Fetching flight for flight id: " + flightId);
		Flight flight = flightRepository.findById(flightId).get();
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName());
		passenger.setPhone(request.getPassengerPhone());
		passenger.setEmail(request.getPassengerEmail());
		LOGGER.info("Saving the Passenger:" + passenger);
		Passenger savedPassenger = passengerRepository.save(passenger);
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		LOGGER.info("Saving the reservation: " + reservation);
		Reservation savedReservation = reservationRepository.save(reservation);

		String filePath = ITINERARY_DIR + savedReservation.getId() + ".pdf";
		LOGGER.info("Genrating Itinerary");
		pdfGenerator.generateItinerary(savedReservation, filePath);
		LOGGER.info("Email the itinerary");
		emailUtil.sendItinerary(passenger.getEmail(), filePath);
		return savedReservation;
	}

}
