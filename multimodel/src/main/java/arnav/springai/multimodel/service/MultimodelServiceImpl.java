package arnav.springai.multimodel.service;

import arnav.springai.multimodel.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class MultimodelServiceImpl implements MultimodelService {

    private final ChatClient openAiChatClient;
    private final ChatClient ollamaChatClient;

    public MultimodelServiceImpl(ChatClient openAiChatClient,ChatClient ollamaChatClient) {
        this.openAiChatClient = openAiChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }


    @Override
    public String getInformationFromOpenAI(Question question) {

        UserMessage userMessage = UserMessage.builder()
                .text(question.question())
                .build();
        Prompt prompt = Prompt.builder()
                .messages(userMessage)
                .build();

        return openAiChatClient.prompt(prompt).call().content();
    }

    @Override
    public String getInformationFromOllama(Question question) {

        UserMessage userMessage = UserMessage.builder()
                .text(question.question())
                .build();
        Prompt prompt = Prompt.builder()
                .messages(userMessage)
                .build();

        return ollamaChatClient.prompt(prompt).call().content();
    }
}
