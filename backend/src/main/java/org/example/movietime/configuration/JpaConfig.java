package org.example.movietime.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//per ora, solo per la data registrazione del cliente
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
