package com.lw.dmappserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class DmAppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmAppServerApplication.class, args);
	}
}
