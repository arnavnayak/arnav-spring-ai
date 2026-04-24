package arnav.springai.springaibasicswithtest.service;

import arnav.springai.springaibasicswithtest.advisors.TokenUsageAuditAdvisor;
import arnav.springai.springaibasicswithtest.model.InvalidAnswerException;
import arnav.springai.springaibasicswithtest.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SpringAIBasicServiceImpl implements SpringAIBasicService {

    private final ChatClient chatClient;
    private FactCheckingEvaluator factCheckingEvaluator;

    public SpringAIBasicServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor()).build();
        this.factCheckingEvaluator = FactCheckingEvaluator.builder(chatClientBuilder).build();
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
    @Retryable(retryFor = InvalidAnswerException.class, maxAttempts = 3)
    @Override
    public String getDefaultInformationBasedOnQuestionAsked(Question question) {

        UserMessage userMessage = UserMessage.builder()
                .text(question.question())
                .build();
        Prompt prompt = Prompt.builder()
                .messages(userMessage)
                .build();

        String response = chatClient.prompt(prompt).call().content();
        validateBadAnswerAtRunTime(question.question(),null,response); //add only if you want to evaluate bad answers during runtime
        return response;
    }

    @Override
    public String getPromptStuffedInformationBasedOnQuestionAsked(Question question) throws IOException {

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
        String response = chatClient.prompt()
                .user(question.question())
                .system(systemPromptStuffedMessage)
                .call().content();
        String context = systemPromptStuffedMessage.getContentAsString(StandardCharsets.UTF_8);
        validateBadAnswerAtRunTime(question.question(),new Document(context),response);
        return response;
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

    private void validateBadAnswerAtRunTime(String question,Document document,String answer){
        EvaluationRequest evaluationRequest = new EvaluationRequest(question, document != null? List.of(document) : List.of(),answer);
        EvaluationResponse response = factCheckingEvaluator.evaluate(evaluationRequest);
        if(!response.isPass()){
            throw new InvalidAnswerException(question,answer);
        }
    }

    @Recover
    private String recover(InvalidAnswerException e){
        return "I'm Sorry , I could not answer your question. Please try re-phrasing it.";
    }
}
