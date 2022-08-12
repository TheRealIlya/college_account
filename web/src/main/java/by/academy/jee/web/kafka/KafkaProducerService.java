package by.academy.jee.web.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    @Value("${kafka.topic.request.log}")
    private String requestLogTopicName;

    @Value("${kafka.topic.token.grant.log}")
    private String tokenGrantLogTopicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void logRequest(String request) {
        kafkaTemplate.send(requestLogTopicName, request);
    }

    public void logTokenGrant(String message) {
        kafkaTemplate.send(tokenGrantLogTopicName, message);
    }

}
