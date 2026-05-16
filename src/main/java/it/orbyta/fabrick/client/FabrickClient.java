package it.orbyta.fabrick.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.orbyta.fabrick.config.FabrickProperties;
import it.orbyta.fabrick.dto.response.BalanceResponse;
import it.orbyta.fabrick.exception.FabrickApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@Component
public class FabrickClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FabrickProperties fabrickProperties;

    @Autowired
    private ObjectMapper objectMapper;

    public BalanceResponse getBalance(Long accountId) {
        log.info("Calling Fabrick getBalance. accountId={}", accountId);
        String url = getBaseUrl() + "/accounts/" + accountId + "/balance";
        try {
            HttpEntity<Object> httpEntity = new HttpEntity<>(null, buildHeaders());
            ResponseEntity<BalanceResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<BalanceResponse>() {
            });
            return responseEntity.getBody();
        } catch (HttpStatusCodeException ex) {
            throw buildFabrickApiException(ex);
        }
    }

    private String getBaseUrl() {
        return fabrickProperties.getBaseUrl() + "/api/gbs/banking/" + fabrickProperties.getApiVersion();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Auth-Schema", fabrickProperties.getAuthSchema());
        headers.set("Api-Key", fabrickProperties.getApiKey());
        headers.set("X-Time-Zone", fabrickProperties.getTimeZone());
        return headers;
    }

    private FabrickApiException buildFabrickApiException(HttpStatusCodeException ex) {
        HttpStatus status = ex.getStatusCode();
        return new FabrickApiException(status, "FABRICK_HTTP_" + status.value(), ex.getResponseBodyAsString());
    }
}
