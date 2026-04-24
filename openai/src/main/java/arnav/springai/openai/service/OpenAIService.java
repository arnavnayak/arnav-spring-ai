package arnav.springai.openai.service;

import arnav.springai.openai.model.Question;

public interface OpenAIService {

    public String getInformation(Question question);
}
