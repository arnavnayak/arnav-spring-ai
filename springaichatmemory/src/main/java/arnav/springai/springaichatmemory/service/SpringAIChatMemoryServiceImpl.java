package arnav.springai.springaichatmemory.service;

import arnav.springai.springaichatmemory.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class SpringAIChatMemoryServiceImpl implements SpringAIChatMemoryService {

    private final ChatClient chatClient;

    public SpringAIChatMemoryServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Value("classpath:/promptTemplates/systemPromptStuffingTemplate.st")
    private Resource systemPromptStuffedMessage;

    @Value("classpath:/promptTemplates/systemPromptTemplateEmailExample.st")
    private Resource systemEmailPromptTemplate;

    @Value("classpath:/promptTemplates/userPromptTemplateEmailExample.st")
    private Resource userEmailPromptTemplate;

    @Override
    public ResponseEntity<String> getDefaultInformation(Question question,String userName) {
        return ResponseEntity.ok(chatClient.prompt()
                .user(question.question())
                        .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID,userName))
                .call().content());
    }

}
