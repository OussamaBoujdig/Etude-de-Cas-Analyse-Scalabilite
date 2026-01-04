package com.hotel.soap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.*;

/**
 * SOAP DTO for Chambre (JAXB-annotated)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChambreSoapDTO")
public class ChambreSoapDTO {
    private Long id;
    private String type;
    private String prix;
    private Boolean disponible;
}
