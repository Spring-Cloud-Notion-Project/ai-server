package ufrn.imd.ai_server.controllers;

import org.springframework.web.bind.annotation.*;
import ufrn.imd.ai_server.services.ChatService;
import ufrn.imd.ai_server.models.NotionRequest;
import ufrn.imd.ai_server.models.Request;
import ufrn.imd.ai_server.services.EmbeddingService;

@RestController
public class ChatController {
    private final ChatService chatService;

    private final EmbeddingService embeddingService;

    public ChatController(ChatService chatService, EmbeddingService embeddingService) {
        this.chatService = chatService;
        this.embeddingService = embeddingService;
    }

    @PostMapping("/notion")
    public String chatWithAINotion(@RequestBody NotionRequest notionRequest) {
        return chatService.getNotionResponse(notionRequest);
    }

//    @PostMapping("/chat")
//    public String chatWithAINotion(@RequestBody String prompt) {
//        return chatService.getChatResponse(prompt);
//    }

    @GetMapping("embedding")
    public String getEmbedding(@RequestParam String question) {
        return embeddingService.findInvestments(question);
    }

}