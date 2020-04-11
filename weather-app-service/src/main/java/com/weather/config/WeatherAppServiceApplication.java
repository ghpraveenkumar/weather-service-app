package com.weather.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@ComponentScan("com.weather.*") 
@SpringBootApplication
public class WeatherAppServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppServiceApplication.class, args);
	}

	@Bean
	public RestTemplate rest() {
	return new RestTemplate();
	}
}
