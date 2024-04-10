package dev.adrianalonso.dekra.quickprod.redis;

import io.lettuce.core.resource.ClientResources;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisClientConnection {

    private RedisProperties redisProperties;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {

        var config = new RedisStandaloneConfiguration(
                                            redisProperties.getHost(),
                                            redisProperties.getPort());
        config.setPassword(RedisPassword.of(redisProperties.getPassword()));

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(30))  // Añadir timeout de 30 segundos
                .build();

        return new LettuceConnectionFactory(config, clientConfig);    }
}



