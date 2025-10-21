package ufrn.imd.ai_server.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.imd.ai_server.models.NotionRequest;
import ufrn.imd.ai_server.models.Request;

public interface ChatService {
    Flux<String> getNotionResponse(NotionRequest notionRequest);

    //String getChatResponse(String prompt);

}
