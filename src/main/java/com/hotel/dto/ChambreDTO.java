package com.hotel.dto;

import com.hotel.entity.TypeChambre;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO for Chambre entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChambreDTO {
    private Long id;
    private TypeChambre type;
    private BigDecimal prix;
    private Boolean disponible;
}
