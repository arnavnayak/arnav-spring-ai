package arnav.springai.springaitool.service;

import arnav.springai.springaitool.model.Question;
import org.springframework.http.ResponseEntity;

public interface SpringAIToolService {

    public ResponseEntity<String> getLocalTime(String question, String userName);

}
