package arnav.springai.springaichatmemory.service;

import arnav.springai.springaichatmemory.model.Question;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;

public interface SpringAIChatMemoryService {

    public ResponseEntity<String> getDefaultInformation(Question question,String userName);

    }
