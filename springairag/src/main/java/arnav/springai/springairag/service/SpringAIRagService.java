package arnav.springai.springairag.service;

import arnav.springai.springairag.model.Question;
import org.springframework.http.ResponseEntity;

public interface SpringAIRagService {

    public ResponseEntity<String> getRandomInformation(Question question, String userName);

    public ResponseEntity<String> getDocumentInformation(Question question, String userName);

    public ResponseEntity<String> getWebSearchInformation(Question question, String userName);
}
