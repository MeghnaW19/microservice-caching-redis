package com.stackroute.caching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/*
	to enable caching feature
*/
@EnableCaching
/**
 * Enables Spring Boot auto config and component scanning.
 */
@SpringBootApplication
public class TrackApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrackApplication.class, args);
	}

}
