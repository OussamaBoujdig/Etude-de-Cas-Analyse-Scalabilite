package com.hotel.soap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.*;

/**
 * SOAP DTO for Reservation (JAXB-annotated)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReservationSoapDTO")
@XmlRootElement(name = "reservation")
public class ReservationSoapDTO {
    private Long id;
    private ClientSoapDTO client;
    private ChambreSoapDTO chambre;
    private String dateDebut;
    private String dateFin;
    private String preferences;
}
