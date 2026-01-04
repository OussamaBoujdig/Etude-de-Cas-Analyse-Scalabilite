package com.hotel.mapper;

import com.hotel.dto.*;
import com.hotel.entity.*;
import org.mapstruct.*;

/**
 * MapStruct mapper for Entity <-> DTO conversions
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    // Client mappings
    ClientDTO toClientDTO(Client client);

    Client toClient(ClientDTO dto);

    // Chambre mappings
    ChambreDTO toChambreDTO(Chambre chambre);

    Chambre toChambre(ChambreDTO dto);

    // Reservation mappings
    @Mapping(target = "client", source = "client")
    @Mapping(target = "chambre", source = "chambre")
    ReservationDTO toReservationDTO(Reservation reservation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "chambre", ignore = true)
    Reservation toReservation(ReservationRequestDTO dto);

    // Update existing reservation from request DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "chambre", ignore = true)
    void updateReservationFromDTO(ReservationRequestDTO dto, @MappingTarget Reservation reservation);
}
