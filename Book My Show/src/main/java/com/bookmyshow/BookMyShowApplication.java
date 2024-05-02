package com.bookmyshow;

import com.bookmyshow.controllers.UserController;
import com.bookmyshow.dto.SignUpRequestDTO;
import com.bookmyshow.dto.SignUpResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyShowApplication implements CommandLineRunner {
	@Autowired
	private UserController userController;

	@Override
	public void run(String... args) throws Exception {
		SignUpRequestDTO signUpRequest = new SignUpRequestDTO();
		signUpRequest.setEmail("harshit@scaler.com");
		signUpRequest.setPassword("test123");

		SignUpResponseDTO signUpResponse = userController.signUp(signUpRequest);
		System.out.println("Debug");
	}

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);


	}

}
