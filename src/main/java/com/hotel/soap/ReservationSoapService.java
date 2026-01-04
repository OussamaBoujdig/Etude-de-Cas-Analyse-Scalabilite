package com.hotel.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

/**
 * SOAP Web Service interface for Reservation operations
 */
@WebService(name = "ReservationSoapService", targetNamespace = "http://soap.hotel.com/")
public interface ReservationSoapService {

    @WebMethod(operationName = "createReservation")
    @WebResult(name = "reservation")
    ReservationSoapDTO createReservation(
            @WebParam(name = "clientId") Long clientId,
            @WebParam(name = "chambreId") Long chambreId,
            @WebParam(name = "dateDebut") String dateDebut,
            @WebParam(name = "dateFin") String dateFin,
            @WebParam(name = "preferences") String preferences);

    @WebMethod(operationName = "getReservationById")
    @WebResult(name = "reservation")
    ReservationSoapDTO getReservationById(@WebParam(name = "id") Long id);

    @WebMethod(operationName = "updateReservation")
    @WebResult(name = "reservation")
    ReservationSoapDTO updateReservation(
            @WebParam(name = "id") Long id,
            @WebParam(name = "clientId") Long clientId,
            @WebParam(name = "chambreId") Long chambreId,
            @WebParam(name = "dateDebut") String dateDebut,
            @WebParam(name = "dateFin") String dateFin,
            @WebParam(name = "preferences") String preferences);

    @WebMethod(operationName = "deleteReservation")
    @WebResult(name = "success")
    boolean deleteReservation(@WebParam(name = "id") Long id);
}
