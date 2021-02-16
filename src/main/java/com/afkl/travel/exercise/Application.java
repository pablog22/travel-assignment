package com.afkl.travel.exercise;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.afkl.travel.exercise")
public class Application {

    @Bean
    @Primary
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .setScriptEncoding("UTF-8")
            .setName("travel-api")
            .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
