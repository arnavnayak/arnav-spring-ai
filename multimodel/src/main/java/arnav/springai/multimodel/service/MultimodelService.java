package arnav.springai.multimodel.service;

import arnav.springai.multimodel.model.Question;

public interface MultimodelService {

    public String getInformationFromOpenAI(Question question);

    public String getInformationFromOllama(Question question);
}
