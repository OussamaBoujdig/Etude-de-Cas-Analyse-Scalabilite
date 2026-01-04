package com.hotel.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GraphQL Input type for Reservation mutations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationInput {
    private Long clientId;
    private Long chambreId;
    private String dateDebut;
    private String dateFin;
    private String preferences;
}
