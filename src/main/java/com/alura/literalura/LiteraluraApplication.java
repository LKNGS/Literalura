package com.alura.literalura;

import com.alura.literalura.principal.Principal;;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class LiteraluraApplication {
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Bean
	@Profile("console")
	public CommandLineRunner commandLineRunner(Principal principal) {
		return args -> principal.muestraElMenu();
	}
}
