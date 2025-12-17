package ufrn.imd.ai_server.events.saga;

import java.time.Instant;

public interface DomainEvent {
    Instant createdAt();
}
