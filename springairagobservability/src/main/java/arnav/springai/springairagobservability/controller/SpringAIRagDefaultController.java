package arnav.springai.springairagobservability.controller;

import arnav.springai.springairagobservability.model.Question;
import arnav.springai.springairagobservability.service.SpringAIRagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpringAIRagDefaultController {

    private final SpringAIRagService springAIRagService;

    public SpringAIRagDefaultController(SpringAIRagService springAIRagService) {
        this.springAIRagService = springAIRagService;
    }

    @GetMapping(value = "/random_rag/ask")
    public ResponseEntity<String> getRandomInformation(@RequestParam("question") Question question,@RequestHeader("userName") String userName){
        return springAIRagService.getRandomInformation(question,userName);
    }

    @GetMapping(value = "/document_rag/ask")
    public ResponseEntity<String> getDocumentInformation(@RequestParam("question") Question question,@RequestHeader("userName") String userName){
        return springAIRagService.getDocumentInformation(question,userName);
    }

    @GetMapping(value = "/web_search_rag/ask")
    public ResponseEntity<String> getWebSearchInformation(@RequestParam("question") Question question,@RequestHeader("userName") String userName){
        return springAIRagService.getWebSearchInformation(question,userName);
    }


}
