package com.springboot.coronovirustracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoronovirustrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronovirustrackerApplication.class, args);
	}

}
