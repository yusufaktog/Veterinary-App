package com.aktog.yusuf.veteriner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class VeterinaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeterinaryApplication.class, args);

	}

	@Bean
	BCryptPasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder();
	}

}
