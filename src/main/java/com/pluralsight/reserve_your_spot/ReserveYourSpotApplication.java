package com.pluralsight.reserve_your_spot;

import com.pluralsight.reserve_your_spot.model.OfficeRoom;
import com.pluralsight.reserve_your_spot.model.WorkStation;
import com.pluralsight.reserve_your_spot.repository.OfficeRoomRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReserveYourSpotApplication {


	public static void main(String[] args) {

		SpringApplication.run(ReserveYourSpotApplication.class, args);

	}

}
