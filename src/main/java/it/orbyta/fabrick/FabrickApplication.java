package it.orbyta.fabrick;

import it.orbyta.fabrick.config.FabrickProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FabrickProperties.class)
public class FabrickApplication {

    public static void main(String[] args) {
        SpringApplication.run(FabrickApplication.class, args);
    }
}
