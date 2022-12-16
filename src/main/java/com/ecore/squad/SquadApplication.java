package com.ecore.squad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SquadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SquadApplication.class, args);
	}

}
