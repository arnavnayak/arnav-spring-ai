package arnav.springai.springaibasicswithtest.service;

import arnav.springai.springaibasicswithtest.model.Question;
import org.junit.jupiter.api.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = {
        "spring.ai.openai.api-key=${OPENAI_API_KEY:test-key}",
        "logging.level.org.springframework.ai=DEBUG"
})
class SpringAIBasicServiceImplTest {

    @Autowired
    private SpringAIBasicServiceImpl springAIBasicService;

    @Autowired
    private ChatModel chatModel;

    private ChatClient chatClient;

    private RelevancyEvaluator relevancyEvaluator;

    private FactCheckingEvaluator factCheckingEvaluator;

    @Value("${test.relevancy.min-score:0.7}")
    private float minRelevancyScore;

    @Value("classpath:/promptTemplates/systemPromptStuffingTemplate.st")
    private Resource systemPromptStuffedMessage;

    @BeforeEach
    void setUp() {
       ChatClient.Builder chatClientBuilder = ChatClient.builder(chatModel).defaultAdvisors(new SimpleLoggerAdvisor());
        this.chatClient = chatClientBuilder.build();
        this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
        this.factCheckingEvaluator = FactCheckingEvaluator.builder(chatClientBuilder).build();
    }

//    @Test
//    void getDefaultInformationTest() {
//    }
//
//    @Test
//    void getDefaultInformationWithAdvisor() {
//    }
//
//    @Test
//    void getDefaultInformationStream() {
//    }
//

    @Test
    @DisplayName("Should return relevant response for basic geography question")
    @Timeout(value = 30)
    void getDefaultInformationBasedOnQuestionAskedTest() {
        //Given
        Question question = new Question("What is the capital of India ?");

        //When
        String aiResponse = springAIBasicService.getDefaultInformationBasedOnQuestionAsked(question);
        EvaluationRequest evaluationRequest = new EvaluationRequest(question.question(),aiResponse);
        EvaluationResponse response = relevancyEvaluator.evaluate(evaluationRequest);

        Assertions.assertAll(()-> assertThat(aiResponse).isNotBlank(),()->assertThat(response.isPass())
                .withFailMessage("""
                        =========================================
                        The answer was not considered relevant.
                        Question: "%s"
                        Response: "%s"
                        =========================================
                        """,question.question(),aiResponse)
                .isTrue(),()-> assertThat(response.getScore())
                .withFailMessage("""
                        =========================================
                        The score %.2f is lower than minimum required %.2f.
                        =========================================
                        """,response.getScore(),minRelevancyScore)
                .isGreaterThan(minRelevancyScore));
    }

    @Test
    @DisplayName("Should return factually correct response for gravity related question")
    @Timeout(value = 30)
    void getDefaultInformationBasedOnQuestionAskedRelatedToGravityTest() {
        //Given
        Question question = new Question("Who discovered the law of universal gravitation ?");

        //When
        String aiResponse = springAIBasicService.getDefaultInformationBasedOnQuestionAsked(question);
        EvaluationRequest evaluationRequest = new EvaluationRequest(question.question(),aiResponse);
        EvaluationResponse response = factCheckingEvaluator.evaluate(evaluationRequest);

        Assertions.assertAll(()-> assertThat(aiResponse).isNotBlank(),()->assertThat(response.isPass())
                .withFailMessage("""
                        =========================================
                        The answer was not considered factually correct
                        Question: "%s"
                        Response: "%s"
                        =========================================
                        """,question.question(),aiResponse)
                .isTrue());
    }

    @Test
    @DisplayName("Should return factually correct response for HR policy related question")
    @Timeout(value = 30)
    void getPromptStuffedInformationBasedOnQuestionAskedTest() throws IOException {
        //Given
        Question question = new Question("What is is total leave I am provided annually ?");
        String retrievedContext = systemPromptStuffedMessage.getContentAsString(StandardCharsets.UTF_8);
        //When
        String aiResponse = springAIBasicService.getPromptStuffedInformationBasedOnQuestionAsked(question);
        EvaluationRequest evaluationRequest = new EvaluationRequest(question.question(), List.of(new Document(retrievedContext)),aiResponse);
        EvaluationResponse response = factCheckingEvaluator.evaluate(evaluationRequest);


        Assertions.assertAll(()-> assertThat(aiResponse).isNotBlank(),()->assertThat(response.isPass())
                .withFailMessage("""
                        =========================================
                        The answer was not considered factually correct
                        Question: "%s"
                        Response: "%s"
                        =========================================
                        """,question.question(),aiResponse)
                .isTrue());
    }
//
//    @Test
//    void getEmailReply() {
//    }
}