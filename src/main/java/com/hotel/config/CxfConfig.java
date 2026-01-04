package com.hotel.config;

import com.hotel.soap.ReservationSoapServiceImpl;
import jakarta.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Apache CXF Configuration for SOAP endpoints
 */
@Configuration
@RequiredArgsConstructor
public class CxfConfig {

    private final Bus bus;
    private final ReservationSoapServiceImpl reservationSoapService;

    @Bean
    public Endpoint reservationEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, reservationSoapService);
        endpoint.publish("/reservation");
        return endpoint;
    }
}
