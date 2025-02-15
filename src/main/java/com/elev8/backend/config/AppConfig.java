package com.elev8.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {
    @Bean
    public Cloudinary getCloudinary() {

        Map config= new HashMap();
        config.put("cloud_name", "dpb7jxbxn");
        config.put("api_key", "349511311786828");
        config.put("api_secret", "9xBfqVpoKYBf-qMxIemfxz5thEg");

        return new Cloudinary(config);
    }
}
