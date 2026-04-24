package arnav.springai.ollama.service;

import arnav.springai.ollama.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class OllamaServiceImpl implements  OllamaService{

    private final ChatClient chatClient;

    public OllamaServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @Override
    public String getInformation(Question question) {

        UserMessage userMessage = UserMessage.builder()
                .text(question.question())
                .build();
        Prompt prompt = Prompt.builder()
                .messages(userMessage)
                .build();

        return chatClient.prompt(prompt).call().content();
    }
}
