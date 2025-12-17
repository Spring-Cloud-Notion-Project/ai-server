package ufrn.imd.ai_server.events.saga;

import ufrn.imd.ai_server.models.NotionRequest;

import java.time.Instant;
import java.util.UUID;

public sealed interface ReportEvent extends DomainEvent, ReportSaga {

    // eventos que serão emitidos pelo operation service
    record ReportRequested(UUID reportId, NotionRequest payload) implements ReportEvent {
        @Override
        public Instant createdAt() {
            return null;
        }
    }

    record ReportContentGenerated(UUID reportId, String content) implements ReportEvent {
        @Override
        public Instant createdAt() {
            return null;
        }
    }

    record ReportFailed(UUID reportId) implements ReportEvent {
        @Override
        public Instant createdAt() {
            return null;
        }
    }
}