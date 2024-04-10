package dev.adrianalonso.dekra.quickprod;

import dev.adrianalonso.dekra.quickprod.redis.RedisProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class QuickprodApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickprodApplication.class, args);
	}

}
