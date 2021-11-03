package com.pluralsight.reserve_your_spot;

import com.pluralsight.reserve_your_spot.config.SwaggerConfig;
import com.pluralsight.reserve_your_spot.model.OfficeRoom;
import com.pluralsight.reserve_your_spot.model.WorkStation;
import com.pluralsight.reserve_your_spot.repository.OfficeRoomRepository;
import com.pluralsight.reserve_your_spot.service.SampleDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileNotFoundException;

@SpringBootApplication
public class ReserveYourSpotApplication {

	public static void main(String[] args){

		SpringApplication.run(ReserveYourSpotApplication.class, args);


	}


}
