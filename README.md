# Fabrick Backend Test

Progetto Spring Boot Java 11 per il test backend Fabrick/Orbyta.

## Stack

- Java 11
- Spring Boot 2.7.18
- RestTemplate per chiamate sincrone cross-service
- Spring Validation
- Swagger/Springfox con `@ApiOperation`, `@ApiResponses`, `@ApiResponse`
- H2 + Spring Data JPA per storicizzazione facoltativa dei movimenti
- JUnit 5 / Mockito / MockMvc

## Configurazione

Le proprietà sono in `src/main/resources/application.yml`.

```yaml
fabrick:
  base-url: https://sandbox.platfr.io
  auth-schema: S2S
  api-key: ${FABRICK_API_KEY:FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP}
  account-id: 14537780
  api-version: v4.0
  time-zone: Europe/Rome
```

In locale è preferibile impostare la API key via variabile d'ambiente:

```bash
export FABRICK_API_KEY=...
```

## Endpoint applicativi

```http
GET  /api/accounts/{accountId}/balance
GET  /api/accounts/{accountId}/transactions?fromAccountingDate=2019-01-01&toAccountingDate=2019-12-01
GET  /api/accounts/{accountId}/transactions/stored
POST /api/accounts/{accountId}/money-transfers
```

## Esempio body bonifico

```json
{
  "creditor": {
    "name": "Mario Rossi",
    "account": {
      "accountCode": "IT23A0336844430152923804660"
    }
  },
  "description": "Bonifico test",
  "currency": "EUR",
  "amount": 1.00,
  "executionDate": "2019-04-01"
}
```

Il conto sandbox del test dovrebbe restituire KO sul bonifico, con errore simile a:

```json
{
  "code": "API000",
  "description": "Errore tecnico  La condizione BP049 non e' prevista per il conto id 14537780"
}
```

## Avvio

```bash
mvn spring-boot:run
```

Swagger UI:

```text
http://localhost:8080/swagger-ui/
```

H2 console:

```text
http://localhost:8080/h2-console
```

## Test

```bash
mvn test
```
