package br.com.despesas;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API - Sistema Para Gerenciamento De Despesas",
				version = "1.0",
				description = "API para cadastro de dispesas.",
				contact = @Contact(name = "Carlos Roberto", email = "crrsj1@gmail.com")
		)
)
public class MinhasDespesasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinhasDespesasApplication.class, args);
	}

}
