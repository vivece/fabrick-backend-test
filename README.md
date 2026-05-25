# Fabrick Backend Test

Progetto Spring Boot Java 17 per il test backend Fabrick/Orbyta.

## Stack

- Java Version 17
- Spring Boot 2.7.18
- RestTemplate per chiamate sincrone cross-service
- Spring Validation (Bean Validation + custom ConstraintValidator)
- Swagger/Springfox con `@ApiOperation`, `@ApiResponses`, `@ApiResponse`
- H2 + Spring Data JPA per storicizzazione facoltativa dei movimenti
- JUnit 5 / Mockito / MockMvc

## Configurazione

Le proprietà sono in `src/main/resources/application.yml`.

## Endpoint applicativi

Tutte le risposte sono wrappate in `FabrickResponse<T>`:

```json
{
  "status": "OK",
  "errors": [],
  "payload": {}
}
```

```http
GET  /api/accounts/{accountId}/balance
GET  /api/accounts/{accountId}/transactions/stored
GET  /api/accounts/{accountId}/transactions?fromAccountingDate=yyyy-MM-dd&toAccountingDate=yyyy-MM-dd
POST /api/accounts/{accountId}/money-transfers
```

Swagger UI:

```
http://localhost:8080/swagger-ui/
```

H2 console:

```
http://localhost:8080/h2-console
url connection: jdbc:h2:mem:fabrickdb;
```


