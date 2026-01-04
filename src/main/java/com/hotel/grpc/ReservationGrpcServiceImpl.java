package com.hotel.grpc;

import com.hotel.dto.ReservationDTO;
import com.hotel.dto.ReservationRequestDTO;
import com.hotel.grpc.generated.*;
import com.hotel.service.ReservationService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDate;

/**
 * gRPC Service implementation for Reservation operations.
 * Extends the generated ImplBase and delegates to the shared
 * ReservationService.
 */
@GrpcService
@RequiredArgsConstructor
@Slf4j
public class ReservationGrpcServiceImpl extends ReservationGrpcServiceGrpc.ReservationGrpcServiceImplBase {

    private final ReservationService reservationService;

    @Override
    public void createReservation(CreateReservationRequest request,
            StreamObserver<ReservationResponse> responseObserver) {
        log.info("gRPC: Creating reservation");

        try {
            ReservationRequestDTO requestDTO = ReservationRequestDTO.builder()
                    .clientId(request.getClientId())
                    .chambreId(request.getChambreId())
                    .dateDebut(LocalDate.parse(request.getDateDebut()))
                    .dateFin(LocalDate.parse(request.getDateFin()))
                    .preferences(request.getPreferences())
                    .build();

            ReservationDTO result = reservationService.createReservation(requestDTO);

            ReservationResponse response = ReservationResponse.newBuilder()
                    .setReservation(convertToProto(result))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("gRPC Error creating reservation: {}", e.getMessage());
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getReservation(GetReservationRequest request,
            StreamObserver<ReservationResponse> responseObserver) {
        log.info("gRPC: Fetching reservation with id: {}", request.getId());

        try {
            ReservationDTO result = reservationService.getReservationById(request.getId());

            ReservationResponse response = ReservationResponse.newBuilder()
                    .setReservation(convertToProto(result))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("gRPC Error fetching reservation: {}", e.getMessage());
            responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void updateReservation(UpdateReservationRequest request,
            StreamObserver<ReservationResponse> responseObserver) {
        log.info("gRPC: Updating reservation with id: {}", request.getId());

        try {
            ReservationRequestDTO requestDTO = ReservationRequestDTO.builder()
                    .clientId(request.getClientId())
                    .chambreId(request.getChambreId())
                    .dateDebut(LocalDate.parse(request.getDateDebut()))
                    .dateFin(LocalDate.parse(request.getDateFin()))
                    .preferences(request.getPreferences())
                    .build();

            ReservationDTO result = reservationService.updateReservation(request.getId(), requestDTO);

            ReservationResponse response = ReservationResponse.newBuilder()
                    .setReservation(convertToProto(result))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("gRPC Error updating reservation: {}", e.getMessage());
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void deleteReservation(DeleteReservationRequest request,
            StreamObserver<DeleteReservationResponse> responseObserver) {
        log.info("gRPC: Deleting reservation with id: {}", request.getId());

        try {
            boolean success = reservationService.deleteReservation(request.getId());

            DeleteReservationResponse response = DeleteReservationResponse.newBuilder()
                    .setSuccess(success)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("gRPC Error deleting reservation: {}", e.getMessage());
            responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        }
    }

    /**
     * Convert ReservationDTO to Protocol Buffer message
     */
    private ReservationProto convertToProto(ReservationDTO dto) {
        ReservationProto.Builder builder = ReservationProto.newBuilder()
                .setId(dto.getId())
                .setDateDebut(dto.getDateDebut().toString())
                .setDateFin(dto.getDateFin().toString());

        if (dto.getPreferences() != null) {
            builder.setPreferences(dto.getPreferences());
        }

        if (dto.getClient() != null) {
            builder.setClient(ClientProto.newBuilder()
                    .setId(dto.getClient().getId())
                    .setNom(dto.getClient().getNom())
                    .setPrenom(dto.getClient().getPrenom())
                    .setEmail(dto.getClient().getEmail())
                    .setTelephone(dto.getClient().getTelephone())
                    .build());
        }

        if (dto.getChambre() != null) {
            ChambreType chambreType = dto.getChambre().getType().name().equals("SIMPLE")
                    ? ChambreType.SIMPLE
                    : ChambreType.DOUBLE;

            builder.setChambre(ChambreProto.newBuilder()
                    .setId(dto.getChambre().getId())
                    .setType(chambreType)
                    .setPrix(dto.getChambre().getPrix().doubleValue())
                    .setDisponible(dto.getChambre().getDisponible())
                    .build());
        }

        return builder.build();
    }
}
