package arnav.springai.springaibasicswithtest.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder){
        ChatOptions chatOptions =  ChatOptions.builder()
                .model("gpt-4.1-mini")
                .temperature(0.8)
                .build();

        return chatClientBuilder
                .defaultOptions(chatOptions)
//                .defaultAdvisors(List.of(new TokenUsageAuditAdvisor()))
                .build();
    }
}
