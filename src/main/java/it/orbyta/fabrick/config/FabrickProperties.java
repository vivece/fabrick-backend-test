package it.orbyta.fabrick.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "fabrick")
public class FabrickProperties {

    private String baseUrl;
    private String authSchema;
    private String apiKey;
    private Long accountId;
    private String apiVersion;
    private String timeZone;

}
