package arnav.springai.springaibasics.service;

import arnav.springai.springaibasics.advisors.TokenUsageAuditAdvisor;
import arnav.springai.springaibasics.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class SpringAIBasicServiceImpl implements SpringAIBasicService {

    private final ChatClient chatClient;

    public SpringAIBasicServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Value("classpath:/promptTemplates/systemPromptStuffingTemplate.st")
    private Resource systemPromptStuffedMessage;

    @Value("classpath:/promptTemplates/systemPromptTemplateEmailExample.st")
    private Resource systemEmailPromptTemplate;

    @Value("classpath:/promptTemplates/userPromptTemplateEmailExample.st")
    private Resource userEmailPromptTemplate;

    @Override
    public String getDefaultInformation() {
        return chatClient.prompt().call().content();
    }

    @Override
    public String getDefaultInformationWithAdvisor() {
        return chatClient.prompt()
                .advisors(List.of(new TokenUsageAuditAdvisor()))
                .call()
                .content();
    }

    @Override
    public Flux<String> getDefaultInformationStream() {
        return chatClient.prompt()
                .stream()
                .content();
    }

    @Override
    public String getDefaultInformationBasedOnQuestionAsked(Question question) {

        UserMessage userMessage = UserMessage.builder()
                .text(question.question())
                .build();
        Prompt prompt = Prompt.builder()
                .messages(userMessage)
                .build();

        return chatClient.prompt(prompt).call().content();
    }

    @Override
    public String getPromptStuffedInformationBasedOnQuestionAsked(Question question) {

        // Method 1: bit descriptive also make sure system message is before userMessage in messages as the order matters
//        UserMessage userMessage = UserMessage.builder()
//                .text(question.question())
//                .build();
//
//        SystemMessage systemMessage = SystemMessage.builder()
//                .text(systemPromptStuffedMessage)
//                .build();
//        Prompt prompt = Prompt.builder()
//                .messages(systemMessage,userMessage)
//                .build();
//
//        return chatClient.prompt(prompt).call().content();

        // Method 2: concise and clean here the order of system and user message donot matter as handled by spring AI giving system message top prio over user message
        return chatClient.prompt()
                .user(question.question())
                .system(systemPromptStuffedMessage)
                .call().content();
    }

    @Override
    public String getEmailReply(String customerName, String customerMessage) {
        return chatClient.prompt()
                .system(systemEmailPromptTemplate)
                .user(promptUserSpec -> promptUserSpec.text(userEmailPromptTemplate)
                        .param("customerName",customerName)
                        .param("customerMessage",customerMessage))
                .call()
                .content();
    }
}
