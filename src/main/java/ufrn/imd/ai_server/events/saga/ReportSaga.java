package ufrn.imd.ai_server.events.saga;

import java.util.UUID;

public interface ReportSaga extends Saga {
    UUID reportId();
}
