package com.photography.lithuanian_press_photography;

import com.photography.lithuanian_press_photography.service.PhotoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.photography.lithuanian_press_photography.config.StorageProperties;

import java.util.UUID;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class PhotoRatingApp {

	public static void main(String[] args) {
		SpringApplication.run(PhotoRatingApp.class, args);
	}

//	@Bean
//	CommandLineRunner init(PhotoService photoService) {
//		return (args) -> {
//			photoService.deleteAll();
//			photoService.init(UUID.randomUUID());
//		};
//	}
}
