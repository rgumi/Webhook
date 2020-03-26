package com.telekom.whatsapp.webhook;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EntityScan("com.telekom.whatsapp.entity")
@SpringBootApplication(scanBasePackages = { "com.telekom.whatsapp" })
@EnableJpaRepositories
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class Application {
    public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		SpringApplication.run(Application.class, args);
	}
}