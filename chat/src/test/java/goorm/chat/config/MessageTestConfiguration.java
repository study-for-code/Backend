package goorm.chat.config;

import goorm.chat.repository.MessageRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MessageRepository.class})
public class MessageTestConfiguration {
}
