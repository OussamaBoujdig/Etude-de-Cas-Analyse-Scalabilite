package com.hotel.graphql;

import com.hotel.dto.ReservationDTO;
import com.hotel.dto.ReservationRequestDTO;
import com.hotel.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

/**
 * GraphQL Controller for Reservation operations.
 * Provides Query and Mutation mappings for the GraphQL schema.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class ReservationGraphQLController {

    private final ReservationService reservationService;

    // ==================== QUERIES ====================

    @QueryMapping
    public ReservationDTO reservationById(@Argument Long id) {
        log.info("GraphQL: Fetching reservation with id: {}", id);
        return reservationService.getReservationById(id);
    }

    @QueryMapping
    public List<ReservationDTO> allReservations() {
        log.info("GraphQL: Fetching all reservations");
        return reservationService.getAllReservations();
    }

    @QueryMapping
    public List<ReservationDTO> reservationsByClient(@Argument Long clientId) {
        log.info("GraphQL: Fetching reservations for client: {}", clientId);
        return reservationService.getReservationsByClient(clientId);
    }

    // ==================== MUTATIONS ====================

    @MutationMapping
    public ReservationDTO createReservation(@Argument ReservationInput input) {
        log.info("GraphQL: Creating reservation");
        ReservationRequestDTO request = convertInputToRequest(input);
        return reservationService.createReservation(request);
    }

    @MutationMapping
    public ReservationDTO updateReservation(@Argument Long id, @Argument ReservationInput input) {
        log.info("GraphQL: Updating reservation with id: {}", id);
        ReservationRequestDTO request = convertInputToRequest(input);
        return reservationService.updateReservation(id, request);
    }

    @MutationMapping
    public Boolean deleteReservation(@Argument Long id) {
        log.info("GraphQL: Deleting reservation with id: {}", id);
        return reservationService.deleteReservation(id);
    }

    /**
     * Convert GraphQL input to service request DTO
     */
    private ReservationRequestDTO convertInputToRequest(ReservationInput input) {
        return ReservationRequestDTO.builder()
                .clientId(input.getClientId())
                .chambreId(input.getChambreId())
                .dateDebut(LocalDate.parse(input.getDateDebut()))
                .dateFin(LocalDate.parse(input.getDateFin()))
                .preferences(input.getPreferences())
                .build();
    }
}
