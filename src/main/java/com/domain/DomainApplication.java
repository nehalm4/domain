package com.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.domain.utility.AutoLoadUtility;

@SpringBootApplication
@EnableAsync
public class DomainApplication implements CommandLineRunner {

	@Autowired
	private AutoLoadUtility autoLoadUtility;

	@Override
	public void run(String... args) throws Exception {
		autoLoadUtility.autoLoad();

	}

	public static void main(String[] args) {
		SpringApplication.run(DomainApplication.class, args);
	}

//	@Bean
//	public Docket productApi() {
//		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.domain"))
//				.build();
//	}

}
