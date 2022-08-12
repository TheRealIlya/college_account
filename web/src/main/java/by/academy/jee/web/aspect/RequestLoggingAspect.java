package by.academy.jee.web.aspect;

import by.academy.jee.web.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
public class RequestLoggingAspect {

    private static final String TYPE_URL = "type:url ";
    private final HttpServletRequest httpServletRequest;
    private final KafkaProducerService kafkaProducerService;

    @SneakyThrows
    @Before("within(by.academy.jee.web.controller.rest..*)")
    public void logRequestAndResponse() {
        String requestDetails = TYPE_URL + httpServletRequest.getMethod() + " " +
                ServletUriComponentsBuilder.fromRequest(httpServletRequest).toUriString();
        kafkaProducerService.logRequest(requestDetails);
    }

}
