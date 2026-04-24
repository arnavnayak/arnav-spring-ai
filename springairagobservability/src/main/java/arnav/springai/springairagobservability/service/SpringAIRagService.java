package arnav.springai.springairagobservability.service;

import arnav.springai.springairagobservability.model.Question;
import org.springframework.http.ResponseEntity;

public interface SpringAIRagService {

    public ResponseEntity<String> getRandomInformation(Question question, String userName);

    public ResponseEntity<String> getDocumentInformation(Question question, String userName);

    public ResponseEntity<String> getWebSearchInformation(Question question, String userName);
}
