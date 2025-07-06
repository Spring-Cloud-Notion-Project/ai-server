package ufrn.imd.ai_server;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAIChatService implements ChatService {
    private final ChatClient chatClient;

    public OpenAIChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getResponse(String prompt) {
        String aiResponse = chatClient.prompt()
                .user(prompt)
                .call().content();
        return aiResponse;
    }
}
