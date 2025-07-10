package ufrn.imd.ai_server;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class OpenAIChatService implements ChatService {
    private final ChatClient chatClient;

    @Value("classpath:prompt/PromptUser.st")
    Resource userTemplate;

    @Value("classpath:prompt/PromptSystem.st")
    Resource systemTemplate;

    public OpenAIChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getNotionResponse(NotionRequest notionRequest) {
        String aiResponse = chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(systemTemplate))
                .user(userSpec -> userSpec.text(userTemplate)
                        .param("page", notionRequest.page())
                        .param("goal", notionRequest.prompt()))
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
}
