package com.lifeIsBeautiful.blogrestapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Blog App Rest Apis",
				description = "Spring Boot Blog App Rest Apis Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Srijansil Bohara",
						email = "bohara.srijansil44@gmail.com",
						url = "https://github.com/lifeisbeautiful44/blog-rest-api-lifeisbeautiful"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "[GitHub]",
				url = "https://github.com/lifeisbeautiful44/blog-rest-api-lifeisbeautiful"
		)
)
public class BlogRestApiApplication {

	@Bean
	public ModelMapper mapper()
	{
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogRestApiApplication.class, args);
	}

}
