package arnav.springai.springaibasicswithtest.service;

import arnav.springai.springaibasicswithtest.model.Question;
import reactor.core.publisher.Flux;

import java.io.IOException;

public interface SpringAIBasicService {

    public String getDefaultInformation();

    public String getDefaultInformationWithAdvisor();

    public Flux<String> getDefaultInformationStream();

    public String getDefaultInformationBasedOnQuestionAsked(Question question);

    public String getPromptStuffedInformationBasedOnQuestionAsked(Question question) throws IOException;

    public String getEmailReply(String customerName, String customerMessage);
}
