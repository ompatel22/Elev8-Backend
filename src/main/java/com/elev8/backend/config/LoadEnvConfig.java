package com.elev8.backend.config;

import io.github.cdimascio.dotenv.Dotenv;

public class LoadEnvConfig {
    public static void load(){
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(),entry.getValue()));
    }
}
