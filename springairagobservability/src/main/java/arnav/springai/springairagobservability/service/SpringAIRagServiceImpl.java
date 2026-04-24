package arnav.springai.springairagobservability.service;

import arnav.springai.springairagobservability.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class SpringAIRagServiceImpl implements SpringAIRagService {

    private final ChatClient chatClient;
    private final ChatClient webSearchChatClient;
    private final VectorStore vectorStore;


    @Value("classpath:/promptTemplates/systemPromptRandomDataTemplate.st")
    private Resource systemPromptRandomDataTemplate;

    @Value("classpath:/promptTemplates/systemPromptHRDocumentTemplate.st")
    private Resource systemPromptHRDocumentTemplate;

    public SpringAIRagServiceImpl(@Qualifier("chatMemoryChatClient") ChatClient chatClient,@Qualifier("webSearchRAGChatClient") ChatClient webSearchChatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.webSearchChatClient = webSearchChatClient;
        this.vectorStore = vectorStore;
    }

    @Override
    public ResponseEntity<String> getRandomInformation(Question question, String userName) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(question.question())
                .topK(3)
                .similarityThreshold(0.5)
                .build();

        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);

        String similarContext = similarDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        String answer = chatClient.prompt()
                .system(promptSystemSpec -> promptSystemSpec.text(systemPromptRandomDataTemplate)
                        .param("documents",similarContext))
                .user(question.question())
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID,userName))
                .call().content();
        return ResponseEntity.ok(answer);
    }

    @Override
    public ResponseEntity<String> getDocumentInformation(Question question, String userName) {

        String answer = chatClient.prompt()
                .user(question.question())
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID,userName))
                .call().content();
        return ResponseEntity.ok(answer);
    }

    @Override
    public ResponseEntity<String> getWebSearchInformation(Question question, String userName) {

        String answer = webSearchChatClient.prompt()
                .user(question.question())
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID,userName))
                .call().content();

        return ResponseEntity.ok(answer);
    }

}
