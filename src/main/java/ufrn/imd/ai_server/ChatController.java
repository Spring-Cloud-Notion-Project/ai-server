package ufrn.imd.ai_server;

import com.fasterxml.jackson.databind.annotation.NoClass;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/notion")
    public String chatWithAINotion(@RequestBody NotionRequest notionRequest) {
        return chatService.getNotionResponse(notionRequest);
    }

    @PostMapping("/chat")
    public String chatWithAINotion(@RequestBody String prompt) {
        return chatService.getChatResponse(prompt);
    }
}