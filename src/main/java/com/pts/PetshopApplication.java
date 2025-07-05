package com.pts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.pts"})
public class PetshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetshopApplication.class, args);
	}

}
