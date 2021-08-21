package arn.filipe.fooddelivery;

import arn.filipe.fooddelivery.domain.repository.CustomizedJpaRepository;
import arn.filipe.fooddelivery.infrastructure.repository.CustomizedJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomizedJpaRepositoryImpl.class)

public class FooddeliveryApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		SpringApplication.run(FooddeliveryApiApplication.class, args);
	}

}
