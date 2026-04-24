package arnav.springai.springaimcpclient.service;

import arnav.springai.springaimcpclient.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class SpringAIMCPClientServiceImpl implements SpringAIMCPClientService {

    private final ChatClient chatClient;

    public SpringAIMCPClientServiceImpl(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) {
        this.chatClient = chatClientBuilder
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }


    @Override
    public String getInformation(String username,Question question) {

        UserMessage userMessage = UserMessage.builder()
                .text(question.question()+ " My username is "+ username)
                .build();
        Prompt prompt = Prompt.builder()
                .messages(userMessage)
                .build();

        return chatClient.prompt(prompt).call().content();
    }
}
