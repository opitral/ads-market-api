package com.opitral.ads.market.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.opitral.ads.market.api.repositories.BaseRepositoryImpl;

@SpringBootApplication()
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
