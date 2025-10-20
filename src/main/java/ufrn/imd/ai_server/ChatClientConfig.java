package ufrn.imd.ai_server;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.BaseChatMemoryAdvisor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) {
        return chatClientBuilder
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }
}