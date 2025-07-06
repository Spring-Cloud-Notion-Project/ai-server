package ufrn.imd.ai_server;

import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public String chatWithAI(@RequestBody String prompt) {
        return chatService.getResponse(prompt);
    }
}