package com.hotel.dto;

import lombok.*;

/**
 * DTO for Client entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
}
