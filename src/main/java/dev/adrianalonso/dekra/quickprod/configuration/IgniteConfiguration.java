package dev.adrianalonso.dekra.quickprod.configuration;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class IgniteConfiguration {

    @Bean
    public Ignite igniteInstance() {
        IgniteConfiguration config = new IgniteConfiguration();

        // Configuraciones específicas pueden ser añadidas aquí
        return Ignition.start(config);
    }
}
