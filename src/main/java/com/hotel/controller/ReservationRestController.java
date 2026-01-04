package com.hotel.controller;

import com.hotel.dto.ReservationDTO;
import com.hotel.dto.ReservationRequestDTO;
import com.hotel.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Reservation CRUD operations.
 * Exposes the ReservationService via JSON/HTTP endpoints.
 */
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ReservationRestController {

    private final ReservationService reservationService;

    /**
     * Create a new reservation
     * POST /api/reservations
     */
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(
            @Valid @RequestBody ReservationRequestDTO request) {
        log.info("REST: Creating reservation");
        ReservationDTO created = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get a reservation by ID
     * GET /api/reservations/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        log.info("REST: Fetching reservation with id: {}", id);
        ReservationDTO reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    /**
     * Get all reservations
     * GET /api/reservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        log.info("REST: Fetching all reservations");
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    /**
     * Update an existing reservation
     * PUT /api/reservations/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequestDTO request) {
        log.info("REST: Updating reservation with id: {}", id);
        ReservationDTO updated = reservationService.updateReservation(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a reservation
     * DELETE /api/reservations/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.info("REST: Deleting reservation with id: {}", id);
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get reservations by client ID
     * GET /api/reservations/client/{clientId}
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByClient(
            @PathVariable Long clientId) {
        log.info("REST: Fetching reservations for client: {}", clientId);
        List<ReservationDTO> reservations = reservationService.getReservationsByClient(clientId);
        return ResponseEntity.ok(reservations);
    }
}
