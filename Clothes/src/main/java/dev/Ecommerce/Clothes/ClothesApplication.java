package dev.Ecommerce.Clothes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaRepositories("dev.Ecommerce.Clothes.repository")
@EntityScan("dev.Ecommerce.Clothes.models")
public class ClothesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothesApplication.class, args);
	}

}
