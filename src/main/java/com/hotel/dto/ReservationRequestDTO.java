package com.hotel.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Request DTO for creating/updating reservations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDTO {

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Chambre ID is required")
    private Long chambreId;

    @NotNull(message = "Start date is required")
    private LocalDate dateDebut;

    @NotNull(message = "End date is required")
    private LocalDate dateFin;

    private String preferences;
}
