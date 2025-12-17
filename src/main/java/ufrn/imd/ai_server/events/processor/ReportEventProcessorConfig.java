package ufrn.imd.ai_server.events.processor;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.support.KafkaHeaders;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;
import ufrn.imd.ai_server.events.saga.ReportEvent;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class ReportEventProcessorConfig {
    private static final Logger log = LoggerFactory.getLogger(ReportEventProcessorConfig.class);
    private final ReportEventProcessor<ReportEvent> eventProcessor;

    private static final String TOPIC_SUCCESS = "report.content-generated";
    private static final String TOPIC_ERROR = "report.failed";

    @Bean
    public Function<Flux<ReportEvent.ReportRequested>, Flux<Message<ReportEvent>>> processor() {
        return flux -> flux.flatMap(eventProcessor::process).doOnNext(r -> log.info("Processed event: {}", r))
                .map(this::toMessage);
    }

    private Message<ReportEvent> toMessage(ReportEvent event) {
        String destination = switch (event) {
            case ReportEvent.ReportContentGenerated s -> TOPIC_SUCCESS;
            case ReportEvent.ReportFailed f -> TOPIC_ERROR;
            default -> TOPIC_ERROR;
        };

        return MessageBuilder.withPayload(event)
                //.setHeader(KafkaHeaders.KEY, event.reportId().toString())
                .setHeader("type", event.getClass().getSimpleName())
                .setHeader("spring.cloud.stream.sendto.destination", destination)
                .build();
    }
}
