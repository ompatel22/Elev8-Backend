package com.elev8.backend;

import com.elev8.backend.config.LoadEnvConfig;
import org.bson.io.BsonOutput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		LoadEnvConfig.load();
		System.out.println();
		SpringApplication.run(BackendApplication.class, args);
	}

}
