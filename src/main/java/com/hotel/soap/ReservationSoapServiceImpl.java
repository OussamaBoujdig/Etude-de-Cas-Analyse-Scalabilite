package com.hotel.soap;

import com.hotel.dto.ReservationDTO;
import com.hotel.dto.ReservationRequestDTO;
import com.hotel.service.ReservationService;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * SOAP Web Service implementation for Reservation operations.
 * Delegates to the shared ReservationService.
 */
@WebService(serviceName = "ReservationSoapService", portName = "ReservationSoapPort", targetNamespace = "http://soap.hotel.com/", endpointInterface = "com.hotel.soap.ReservationSoapService")
@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationSoapServiceImpl implements ReservationSoapService {

    private final ReservationService reservationService;

    @Override
    public ReservationSoapDTO createReservation(Long clientId, Long chambreId,
            String dateDebut, String dateFin, String preferences) {
        log.info("SOAP: Creating reservation");

        ReservationRequestDTO request = ReservationRequestDTO.builder()
                .clientId(clientId)
                .chambreId(chambreId)
                .dateDebut(LocalDate.parse(dateDebut))
                .dateFin(LocalDate.parse(dateFin))
                .preferences(preferences)
                .build();

        ReservationDTO result = reservationService.createReservation(request);
        return convertToSoapDTO(result);
    }

    @Override
    public ReservationSoapDTO getReservationById(Long id) {
        log.info("SOAP: Fetching reservation with id: {}", id);
        ReservationDTO result = reservationService.getReservationById(id);
        return convertToSoapDTO(result);
    }

    @Override
    public ReservationSoapDTO updateReservation(Long id, Long clientId, Long chambreId,
            String dateDebut, String dateFin, String preferences) {
        log.info("SOAP: Updating reservation with id: {}", id);

        ReservationRequestDTO request = ReservationRequestDTO.builder()
                .clientId(clientId)
                .chambreId(chambreId)
                .dateDebut(LocalDate.parse(dateDebut))
                .dateFin(LocalDate.parse(dateFin))
                .preferences(preferences)
                .build();

        ReservationDTO result = reservationService.updateReservation(id, request);
        return convertToSoapDTO(result);
    }

    @Override
    public boolean deleteReservation(Long id) {
        log.info("SOAP: Deleting reservation with id: {}", id);
        return reservationService.deleteReservation(id);
    }

    /**
     * Convert ReservationDTO to SOAP-specific DTO
     */
    private ReservationSoapDTO convertToSoapDTO(ReservationDTO dto) {
        ReservationSoapDTO soapDTO = new ReservationSoapDTO();
        soapDTO.setId(dto.getId());
        soapDTO.setDateDebut(dto.getDateDebut().toString());
        soapDTO.setDateFin(dto.getDateFin().toString());
        soapDTO.setPreferences(dto.getPreferences());

        if (dto.getClient() != null) {
            ClientSoapDTO clientSoap = new ClientSoapDTO();
            clientSoap.setId(dto.getClient().getId());
            clientSoap.setNom(dto.getClient().getNom());
            clientSoap.setPrenom(dto.getClient().getPrenom());
            clientSoap.setEmail(dto.getClient().getEmail());
            clientSoap.setTelephone(dto.getClient().getTelephone());
            soapDTO.setClient(clientSoap);
        }

        if (dto.getChambre() != null) {
            ChambreSoapDTO chambreSoap = new ChambreSoapDTO();
            chambreSoap.setId(dto.getChambre().getId());
            chambreSoap.setType(dto.getChambre().getType().name());
            chambreSoap.setPrix(dto.getChambre().getPrix().toString());
            chambreSoap.setDisponible(dto.getChambre().getDisponible());
            soapDTO.setChambre(chambreSoap);
        }

        return soapDTO;
    }
}
