package com.pluralsight.reserve_your_spot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.FileNotFoundException;

@SpringBootApplication
public class ReserveYourSpotApplication {


	public static void main(String[] args) throws FileNotFoundException {

		SpringApplication.run(ReserveYourSpotApplication.class, args);


	}


}
