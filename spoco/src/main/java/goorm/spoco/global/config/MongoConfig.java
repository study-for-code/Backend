package goorm.spoco.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "goorm.spoco.domain.message.repository")
public class MongoConfig {
}
