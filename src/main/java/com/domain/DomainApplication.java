package com.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.domain.utility.AutoLoadUtility;

/**
 * @author Nehal Mahajan
 * @apiNote This class is controller class
 */
@SpringBootApplication
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

}
