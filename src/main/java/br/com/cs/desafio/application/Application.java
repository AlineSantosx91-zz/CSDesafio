package br.com.cs.desafio.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
// @EnableAutoConfiguration
//@ComponentScan("br.com.cs.desafio.**")
//@EnableJpaRepositories(basePackages = { "br.com.cs.desafio.dao.**" })
//@EntityScan(basePackages = { "br.com.cs.*" })
@PropertySource("config/application.properties")
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



}
