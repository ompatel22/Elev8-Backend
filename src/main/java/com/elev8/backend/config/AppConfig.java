package com.elev8.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    private static final Dotenv dotenv = Dotenv.load();

    @Bean
    public Cloudinary getCloudinary() {
        Map config= new HashMap();
        config.put("cloud_name", dotenv.get("CLOUD_NAME"));
        config.put("api_key", dotenv.get("API_KEY"));
        config.put("api_secret", dotenv.get("API_SECRET"));
        return new Cloudinary(config);
    }
}
