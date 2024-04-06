package dev.adrianalonso.dekra.quickprod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static dev.adrianalonso.dekra.quickprod.constants.Constants.STRENGTH;

@SpringBootApplication
public class QuickprodApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickprodApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(STRENGTH);
	}
}
