package goorm.spoco;

import goorm.spoco.global.config.JpaConfig;
import goorm.spoco.global.config.MongoConfig;
import org.aspectj.bridge.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = {"goorm.spoco", "goorm.message"})
@Import({JpaConfig.class, MongoConfig.class})
public class SpocoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpocoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET","POST", "PUT", "PATCH", "DELETE", "OPTIONS");
			}
		};
	}

}
