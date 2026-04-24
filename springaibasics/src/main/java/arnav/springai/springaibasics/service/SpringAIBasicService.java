package arnav.springai.springaibasics.service;

import arnav.springai.springaibasics.model.Question;
import reactor.core.publisher.Flux;

public interface SpringAIBasicService {

    public String getDefaultInformation();

    public String getDefaultInformationWithAdvisor();

    public Flux<String> getDefaultInformationStream();

    public String getDefaultInformationBasedOnQuestionAsked(Question question);

    public String getPromptStuffedInformationBasedOnQuestionAsked(Question question);

    public String getEmailReply(String customerName, String customerMessage);
}
