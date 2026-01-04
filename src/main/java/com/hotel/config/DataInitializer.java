package com.hotel.config;

import com.hotel.entity.*;
import com.hotel.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Data initializer to populate sample data on application startup
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final ChambreRepository chambreRepository;

    @Override
    public void run(String... args) {
        log.info("Initializing sample data...");

        // Create sample clients
        Client client1 = clientRepository.save(Client.builder()
                .nom("Dupont")
                .prenom("Jean")
                .email("jean.dupont@email.com")
                .telephone("+33612345678")
                .build());

        Client client2 = clientRepository.save(Client.builder()
                .nom("Martin")
                .prenom("Marie")
                .email("marie.martin@email.com")
                .telephone("+33623456789")
                .build());

        Client client3 = clientRepository.save(Client.builder()
                .nom("Bernard")
                .prenom("Pierre")
                .email("pierre.bernard@email.com")
                .telephone("+33634567890")
                .build());

        log.info("Created {} sample clients", 3);

        // Create sample chambres (rooms)
        Chambre chambre1 = chambreRepository.save(Chambre.builder()
                .type(TypeChambre.SIMPLE)
                .prix(new BigDecimal("89.99"))
                .disponible(true)
                .build());

        Chambre chambre2 = chambreRepository.save(Chambre.builder()
                .type(TypeChambre.DOUBLE)
                .prix(new BigDecimal("149.99"))
                .disponible(true)
                .build());

        Chambre chambre3 = chambreRepository.save(Chambre.builder()
                .type(TypeChambre.SIMPLE)
                .prix(new BigDecimal("79.99"))
                .disponible(true)
                .build());

        Chambre chambre4 = chambreRepository.save(Chambre.builder()
                .type(TypeChambre.DOUBLE)
                .prix(new BigDecimal("199.99"))
                .disponible(true)
                .build());

        Chambre chambre5 = chambreRepository.save(Chambre.builder()
                .type(TypeChambre.DOUBLE)
                .prix(new BigDecimal("299.99"))
                .disponible(false)
                .build());

        log.info("Created {} sample rooms", 5);
        log.info("Sample data initialization complete!");
    }
}
