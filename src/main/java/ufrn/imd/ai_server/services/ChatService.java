package ufrn.imd.ai_server.services;

import ufrn.imd.ai_server.models.NotionRequest;
import ufrn.imd.ai_server.models.Request;

public interface ChatService {
    String getNotionResponse(NotionRequest notionRequest);

    String getChatResponse(String prompt);

}
