package com.telekom.whatsapp.webhook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.telekom.whatsapp.entity")
@SpringBootApplication(scanBasePackages = { "com.telekom.whatsapp" })
@EnableJpaRepositories
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}