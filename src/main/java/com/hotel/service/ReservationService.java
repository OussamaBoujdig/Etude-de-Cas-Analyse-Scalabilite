package com.hotel.service;

import com.hotel.dto.ReservationDTO;
import com.hotel.dto.ReservationRequestDTO;

import java.util.List;

/**
 * Business service interface for Reservation operations.
 * This single interface is shared across all protocol implementations (REST,
 * SOAP, GraphQL, gRPC).
 */
public interface ReservationService {

    /**
     * Create a new reservation
     * 
     * @param request the reservation request data
     * @return the created reservation
     */
    ReservationDTO createReservation(ReservationRequestDTO request);

    /**
     * Get a reservation by its ID
     * 
     * @param id the reservation ID
     * @return the reservation if found
     */
    ReservationDTO getReservationById(Long id);

    /**
     * Update an existing reservation
     * 
     * @param id      the reservation ID to update
     * @param request the updated reservation data
     * @return the updated reservation
     */
    ReservationDTO updateReservation(Long id, ReservationRequestDTO request);

    /**
     * Delete a reservation by its ID
     * 
     * @param id the reservation ID to delete
     * @return true if deletion was successful
     */
    boolean deleteReservation(Long id);

    /**
     * Get all reservations
     * 
     * @return list of all reservations
     */
    List<ReservationDTO> getAllReservations();

    /**
     * Get all reservations for a specific client
     * 
     * @param clientId the client ID
     * @return list of reservations for the client
     */
    List<ReservationDTO> getReservationsByClient(Long clientId);
}
