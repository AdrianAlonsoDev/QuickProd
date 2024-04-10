package dev.adrianalonso.dekra.quickprod.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "redis.config.cache")
public class RedisProperties {
    private String host;
    private int port;
    private String password;
}
