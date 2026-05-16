package it.orbyta.fabrick;

import it.orbyta.fabrick.config.FabrickProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FabrickProperties.class)
public class FabrickApplication {
//TODO formattazione e optimize import of the project
    //passare ad OpenAPI e cancellare la classe di swagger
    // Long per il getBalance deve essere una stringa

    public static void main(String[] args) {
        SpringApplication.run(FabrickApplication.class, args);
    }
}
