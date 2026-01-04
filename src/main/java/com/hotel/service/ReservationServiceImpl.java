package com.hotel.service;

import com.hotel.dto.ReservationDTO;
import com.hotel.dto.ReservationRequestDTO;
import com.hotel.entity.Chambre;
import com.hotel.entity.Client;
import com.hotel.entity.Reservation;
import com.hotel.exception.ResourceNotFoundException;
import com.hotel.exception.BusinessException;
import com.hotel.mapper.ReservationMapper;
import com.hotel.repository.ChambreRepository;
import com.hotel.repository.ClientRepository;
import com.hotel.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of ReservationService.
 * Thread-safe and transactional business logic layer.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final ChambreRepository chambreRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationDTO createReservation(ReservationRequestDTO request) {
        log.info("Creating reservation for client {} in room {}", request.getClientId(), request.getChambreId());

        // Validate dates
        if (request.getDateDebut().isAfter(request.getDateFin())) {
            throw new BusinessException("Start date must be before end date");
        }

        // Find client
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", request.getClientId()));

        // Find chambre
        Chambre chambre = chambreRepository.findById(request.getChambreId())
                .orElseThrow(() -> new ResourceNotFoundException("Chambre", request.getChambreId()));

        // Check room availability
        if (!chambre.getDisponible()) {
            throw new BusinessException("Room is not available");
        }

        // Check for overlapping reservations
        List<Reservation> overlapping = reservationRepository.findOverlappingReservations(
                chambre.getId(), request.getDateDebut(), request.getDateFin());
        if (!overlapping.isEmpty()) {
            throw new BusinessException("Room is already booked for the selected dates");
        }

        // Create reservation
        Reservation reservation = Reservation.builder()
                .client(client)
                .chambre(chambre)
                .dateDebut(request.getDateDebut())
                .dateFin(request.getDateFin())
                .preferences(request.getPreferences())
                .build();

        Reservation saved = reservationRepository.save(reservation);
        log.info("Created reservation with ID: {}", saved.getId());

        return reservationMapper.toReservationDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDTO getReservationById(Long id) {
        log.debug("Fetching reservation with ID: {}", id);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        return reservationMapper.toReservationDTO(reservation);
    }

    @Override
    public ReservationDTO updateReservation(Long id, ReservationRequestDTO request) {
        log.info("Updating reservation with ID: {}", id);

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));

        // Validate dates
        if (request.getDateDebut().isAfter(request.getDateFin())) {
            throw new BusinessException("Start date must be before end date");
        }

        // Update client if changed
        if (!reservation.getClient().getId().equals(request.getClientId())) {
            Client newClient = clientRepository.findById(request.getClientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Client", request.getClientId()));
            reservation.setClient(newClient);
        }

        // Update chambre if changed
        if (!reservation.getChambre().getId().equals(request.getChambreId())) {
            Chambre newChambre = chambreRepository.findById(request.getChambreId())
                    .orElseThrow(() -> new ResourceNotFoundException("Chambre", request.getChambreId()));

            // Check for overlapping reservations (excluding current reservation)
            List<Reservation> overlapping = reservationRepository.findOverlappingReservations(
                    newChambre.getId(), request.getDateDebut(), request.getDateFin());
            overlapping.removeIf(r -> r.getId().equals(id));

            if (!overlapping.isEmpty()) {
                throw new BusinessException("Room is already booked for the selected dates");
            }
            reservation.setChambre(newChambre);
        }

        reservation.setDateDebut(request.getDateDebut());
        reservation.setDateFin(request.getDateFin());
        reservation.setPreferences(request.getPreferences());

        Reservation updated = reservationRepository.save(reservation);
        log.info("Updated reservation with ID: {}", updated.getId());

        return reservationMapper.toReservationDTO(updated);
    }

    @Override
    public boolean deleteReservation(Long id) {
        log.info("Deleting reservation with ID: {}", id);

        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }

        reservationRepository.deleteById(id);
        log.info("Deleted reservation with ID: {}", id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getAllReservations() {
        log.debug("Fetching all reservations");
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getReservationsByClient(Long clientId) {
        log.debug("Fetching reservations for client: {}", clientId);
        return reservationRepository.findByClientId(clientId).stream()
                .map(reservationMapper::toReservationDTO)
                .collect(Collectors.toList());
    }
}
