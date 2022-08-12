package by.academy.jee.web.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${kafka.topic.request.log}")
    private String requestLogTopicName;

    @Value("${kafka.topic.token.grant.log}")
    private String tokenGrantLogTopicName;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        return new KafkaAdmin(properties);
    }

    @Bean
    public NewTopic requestLogTopic() {
        return new NewTopic(requestLogTopicName, 1, (short) 1);
    }

    @Bean
    public NewTopic tokenGrantLogTopic() {
        return new NewTopic(tokenGrantLogTopicName, 1, (short) 1);
    }

}
