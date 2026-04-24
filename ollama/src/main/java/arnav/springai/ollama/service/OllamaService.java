package arnav.springai.ollama.service;

import arnav.springai.ollama.model.Question;

public interface OllamaService {

    public String getInformation(Question question);
}
