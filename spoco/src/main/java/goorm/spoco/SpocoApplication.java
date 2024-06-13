package goorm.spoco;

import org.aspectj.bridge.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.example.mysqlmongo.model.jpa"])
@EnableMongoRepositories(basePackages = ["com.example.mysqlmongo.model.mongo"])
public class SpocoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpocoApplication.class, args);
	}

}
