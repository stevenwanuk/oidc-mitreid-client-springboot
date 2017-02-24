package com.sven.oidc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})//(exclude = {SecurityAutoConfiguration.class})
public class OidcApplication {

	public static void main(String[] args) {
		SpringApplication.run(OidcApplication.class, args);
	}
}
