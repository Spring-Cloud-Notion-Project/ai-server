package ufrn.imd.ai_server.controllers;

import org.springframework.web.bind.annotation.*;
import ufrn.imd.ai_server.services.ChatService;
import ufrn.imd.ai_server.models.NotionRequest;
import ufrn.imd.ai_server.services.InvestmentsService;

@RestController
public class ChatController {
    private final ChatService chatService;

    private final InvestmentsService investmentsService;

    public ChatController(ChatService chatService, InvestmentsService investmentsService) {
        this.chatService = chatService;
        this.investmentsService = investmentsService;
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
        return investmentsService.findClosestMatch(question);
    }

    @GetMapping("add-data")
    public void addData(){
        investmentsService.save(investmentsService.getInvestments());
    }
}