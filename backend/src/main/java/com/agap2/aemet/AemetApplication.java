package com.agap2.aemet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.agap2.aemet.config.AemetProperties;

@SpringBootApplication
@EnableConfigurationProperties(AemetProperties.class)
public class AemetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AemetApplication.class, args);
	}

}
