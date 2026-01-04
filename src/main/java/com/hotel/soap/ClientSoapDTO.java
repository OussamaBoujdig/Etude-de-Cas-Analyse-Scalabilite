package com.hotel.soap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.*;

/**
 * SOAP DTO for Client (JAXB-annotated)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientSoapDTO")
public class ClientSoapDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
}
