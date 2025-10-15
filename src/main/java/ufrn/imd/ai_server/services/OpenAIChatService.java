package ufrn.imd.ai_server.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ufrn.imd.ai_server.models.NotionRequest;
import ufrn.imd.ai_server.models.Request;
import ufrn.imd.ai_server.services.ChatService;

@Service
public class OpenAIChatService implements ChatService {
    private final ChatClient chatClient;

    @Value("classpath:prompt/PromptUser.st")
    Resource userTemplate;

    @Value("classpath:prompt/PromptSystemMini.st")
    Resource systemTemplate;

    public OpenAIChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getNotionResponse(NotionRequest notionRequest) {
        String aiResponse = chatClient.prompt()
                .user(userSpec -> userSpec.text(userTemplate)
                        .param("page", notionRequest.page())
                        .param("prompt", notionRequest.prompt()))
//                .system(systemSpec -> systemSpec
//                        .text(systemTemplate))
//                .user(userSpec -> userSpec.text(userTemplate)
//                        .param("page", notionRequest.page())
//                        .param("goal", notionRequest.prompt()))
                .call().content();
        //String aiResponse = "{\"teste\": \"Resposta da IA\"}";
        return aiResponse;
    }

    @Override
    public String getChatResponse(String prompt) {
        String aiResponse = chatClient.prompt()
                .user(prompt)
                .call().content();
        return aiResponse;
    }

    @Override
    public String getAIResponse(Request request) {
        String aiResponse = chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(systemTemplate))
                .user(userSpec -> userSpec.text(userTemplate)
                        .param("destination", request.destination())
                        .param("days", request.days()))
                .call().content();
        return aiResponse;
    }
}
