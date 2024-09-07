package com.photography.lithuanian_press_photography;

import com.photography.lithuanian_press_photography.config.StorageProperties;
import com.photography.lithuanian_press_photography.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class PhotoRatingApp {

	public static void main(String[] args) {
		SpringApplication.run(PhotoRatingApp.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
