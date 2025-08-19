package com.budgetbootstrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.modulith.Modulithic;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableWebSecurity
@EnableScheduling
@Modulithic
@SpringBootApplication
public class BudgetBootstrapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetBootstrapperApplication.class, args);
	}

}
