# Hotel Reservation Multi-Protocol API

A Spring Boot application exposing a Hotel Reservation System via **four different protocols**: REST, SOAP, GraphQL, and gRPC.

## Architecture

```
┌──────────────────────────────────────────────────────────────────┐
│                        Interface Layer                           │
├────────────────┬────────────────┬────────────────┬───────────────┤
│      REST      │      SOAP      │    GraphQL     │     gRPC      │
│  (Port 8080)   │  (Port 8080)   │  (Port 8080)   │  (Port 9090)  │
├────────────────┴────────────────┴────────────────┴───────────────┤
│                      Business Layer                               │
│              ReservationService / ReservationServiceImpl          │
├──────────────────────────────────────────────────────────────────┤
│                        DAO Layer                                  │
│    ClientRepository | ChambreRepository | ReservationRepository   │
├──────────────────────────────────────────────────────────────────┤
│                    H2 In-Memory Database                          │
└──────────────────────────────────────────────────────────────────┘
```

## Prerequisites

- Java 17+
- Maven 3.8+

## Build & Run

```bash
# Build the project (generates gRPC classes from .proto)
mvn clean compile

# Run the application
mvn spring-boot:run
```

## Accessing the Interfaces

### REST API

| Operation | Method | URL |
|-----------|--------|-----|
| Create    | POST   | `http://localhost:8080/api/reservations` |
| Read      | GET    | `http://localhost:8080/api/reservations/{id}` |
| Update    | PUT    | `http://localhost:8080/api/reservations/{id}` |
| Delete    | DELETE | `http://localhost:8080/api/reservations/{id}` |
| All       | GET    | `http://localhost:8080/api/reservations` |

**Sample curl:**
```bash
# Create
curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -d '{"clientId":1,"chambreId":1,"dateDebut":"2024-01-15","dateFin":"2024-01-20","preferences":"Vue mer"}'

# Read
curl http://localhost:8080/api/reservations/1
```

### SOAP Service

- **WSDL URL**: `http://localhost:8080/services/reservation?wsdl`
- Use SoapUI or any SOAP client with the WSDL

### GraphQL

- **GraphiQL UI**: `http://localhost:8080/graphiql`
- **Endpoint**: `http://localhost:8080/graphql`

**Sample Queries:**
```graphql
# Query
query {
  reservationById(id: 1) {
    id
    dateDebut
    dateFin
    client { nom prenom email }
    chambre { type prix }
  }
}

# Mutation
mutation {
  createReservation(input: {
    clientId: 1
    chambreId: 1
    dateDebut: "2024-01-15"
    dateFin: "2024-01-20"
    preferences: "Vue mer"
  }) { id }
}
```

### gRPC

- **Port**: `9090`
- Use [grpcurl](https://github.com/fullstorydev/grpcurl) or [BloomRPC](https://github.com/bloomrpc/bloomrpc)

**Sample grpcurl:**
```bash
# List services
grpcurl -plaintext localhost:9090 list

# Create reservation
grpcurl -plaintext -d '{
  "client_id": 1,
  "chambre_id": 1,
  "date_debut": "2024-01-15",
  "date_fin": "2024-01-20",
  "preferences": "Vue mer"
}' localhost:9090 hotel.ReservationGrpcService/CreateReservation
```

### H2 Database Console

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:hoteldb`
- **Username**: `sa`
- **Password**: (empty)

## Project Structure

```
src/main/java/com/hotel/
├── HotelReservationApplication.java    # Main class
├── config/
│   ├── CxfConfig.java                  # SOAP configuration
│   └── DataInitializer.java            # Sample data
├── controller/
│   └── ReservationRestController.java  # REST endpoint
├── dto/                                 # Data Transfer Objects
├── entity/                              # JPA Entities
├── exception/                           # Exception handling
├── graphql/
│   ├── ReservationGraphQLController.java
│   └── ReservationInput.java
├── grpc/
│   └── ReservationGrpcServiceImpl.java
├── mapper/
│   └── ReservationMapper.java          # MapStruct mapper
├── repository/                          # Spring Data JPA
├── service/
│   ├── ReservationService.java         # Business interface
│   └── ReservationServiceImpl.java     # Implementation
└── soap/                                # SOAP DTOs and service

src/main/proto/
└── reservation.proto                    # gRPC definition

src/main/resources/
├── application.properties
└── graphql/
    └── schema.graphqls                  # GraphQL schema
```

## Sample Data

On startup, the application creates:
- **3 Clients**: Jean Dupont, Marie Martin, Pierre Bernard
- **5 Rooms**: Mix of SIMPLE and DOUBLE types with varying prices

## Tech Stack

- Spring Boot 3.2
- Spring Data JPA + H2
- Spring GraphQL
- gRPC Spring Boot Starter
- Apache CXF (SOAP)
- Lombok + MapStruct
#
