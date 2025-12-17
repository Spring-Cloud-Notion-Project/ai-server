package ufrn.imd.ai_server.events.processor;

import reactor.core.publisher.Mono;
import ufrn.imd.ai_server.events.saga.DomainEvent;

public interface EventProcessor<T extends DomainEvent, R extends DomainEvent> {
    Mono<R> process(T event);
}
