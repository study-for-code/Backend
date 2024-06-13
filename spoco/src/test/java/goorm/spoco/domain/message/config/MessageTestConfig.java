package goorm.spoco.domain.message.config;


import goorm.spoco.domain.message.repository.MessageRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MessageRepository.class})
public class MessageTestConfig{
}
