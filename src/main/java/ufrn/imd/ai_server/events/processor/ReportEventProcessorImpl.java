package ufrn.imd.ai_server.events.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import ufrn.imd.ai_server.events.saga.ReportEvent;
import ufrn.imd.ai_server.services.ChatService;

@Service
public class ReportEventProcessorImpl implements ReportEventProcessor<ReportEvent> {
    private static final Logger log = LoggerFactory.getLogger(ReportEventProcessorConfig.class);
    private final ChatService chatService;

    public ReportEventProcessorImpl(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public Mono<ReportEvent> handle(ReportEvent.ReportRequested event) {
        return chatService.getNotionResponse(event.payload())
                .reduce("", (a, b) -> a + b)
                .map(content -> (ReportEvent) new ReportEvent.ReportContentGenerated(event.reportId(), content))
                .onErrorResume(e -> {
                    log.error("Erro processando report {}", event.reportId(), e);
                    return Mono.just(new ReportEvent.ReportFailed(event.reportId()));
                });
    }
}
