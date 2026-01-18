package br.rosa.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class Config {

	@Bean
	public OpenAPI customOpenAPI() {
		// @formatter:off
		return new OpenAPI()
				.info(new Info()
						.title("Test API")
						.version("2.0")
						.description("Beautiful Description")
						.license(new License().name("Rod 2.0").url("https://dee.org")));
		// @formatter:on
	}
}
