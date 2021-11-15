package com.myproject.ReserveYourSpot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.FileNotFoundException;

// Comment: why do we throw FileNotFoundException
// Comment: formatting
// Comment: package path naming, should be something like com.prodyna.reserveyourspot

@SpringBootApplication
public class ReserveYourSpotApplication {


	public static void main(String[] args) throws FileNotFoundException {

		SpringApplication.run(ReserveYourSpotApplication.class, args);


	}


}
