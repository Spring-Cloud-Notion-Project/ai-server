package ufrn.imd.ai_server;

public interface ChatService {
    String getNotionResponse(NotionRequest notionRequest);

    String getChatResponse(String prompt);
}
