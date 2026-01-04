package com.hotel.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * DTO for Reservation entity (response)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {
    private Long id;
    private ClientDTO client;
    private ChambreDTO chambre;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String preferences;
}
