package com.online.Real;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RealApplication {
	@Autowired
	public static void main(String[] args) {
			SpringApplication.run(RealApplication.class, args);
		}
	}
