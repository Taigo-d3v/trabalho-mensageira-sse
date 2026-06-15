package com.trabalho.mensageira_sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MensageiraSseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MensageiraSseApplication.class, args);
	}

}
