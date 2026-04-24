package arnav.springai.springaitool.service;

import arnav.springai.springaitool.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class SpringAIToolServiceImpl implements SpringAIToolService {

    private final ChatClient chatClient;

    public SpringAIToolServiceImpl(@Qualifier("timeChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public ResponseEntity<String> getLocalTime(String question, String userName) {
        String answer = chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID,userName))
                .user(question)
                .call()
                .content();

        return ResponseEntity.ok(answer);
    }
}
