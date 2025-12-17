package ufrn.imd.ai_server.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.imd.ai_server.models.NotionRequest;
import ufrn.imd.ai_server.services.ChatService;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;

import java.time.Duration;

@Service
public class OpenAIChatService implements ChatService {
        private final ChatClient chatClient;

        private final VectorStore vectorStore;

        ChatMemoryRepository chatMemoryRepository = new InMemoryChatMemoryRepository();

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                        .chatMemoryRepository(chatMemoryRepository)
                        .maxMessages(4)
                        .build();

        @Value("classpath:prompt/PromptUser.st")
        Resource userTemplate;

        @Value("classpath:prompt/PromptSystemMini.st")
        Resource systemTemplate;

        public OpenAIChatService(ChatClient chatClient, VectorStore vectorStore) {
                this.chatClient = chatClient;
                this.vectorStore = vectorStore;
        }

//        public Flux<String> getNotionResponse(NotionRequest notionRequest) {
//                return Flux.error(new RuntimeException("Erro Simulado: Falha na conexão com a OpenAI"));
//        }

        @Override
        public Flux<String> getNotionResponse(NotionRequest notionRequest) {
                return chatClient.prompt()
                .advisors(PromptChatMemoryAdvisor.builder(chatMemory).build(), new QuestionAnswerAdvisor(vectorStore))
                        .user(userSpec -> userSpec.text(userTemplate)
                                .param("page", notionRequest.page())
                                .param("prompt", notionRequest.prompt()))
                        .stream()
                        .content();
        }

        // @Override
        // public String getChatResponse(String prompt) {
        // String aiResponse = chatClient.prompt()
        // .user(prompt)
        // .call().content();
        // return aiResponse;
        // }

}
