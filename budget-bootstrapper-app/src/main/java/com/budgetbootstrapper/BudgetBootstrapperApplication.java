package com.budgetbootstrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

@Modulithic
@SpringBootApplication
public class BudgetBootstrapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetBootstrapperApplication.class, args);
	}

}
