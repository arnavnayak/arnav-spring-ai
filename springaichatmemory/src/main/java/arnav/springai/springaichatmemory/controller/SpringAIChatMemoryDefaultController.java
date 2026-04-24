package arnav.springai.springaichatmemory.controller;

import arnav.springai.springaichatmemory.model.Question;
import arnav.springai.springaichatmemory.service.SpringAIChatMemoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
public class SpringAIChatMemoryDefaultController {

    private final SpringAIChatMemoryService springAIBasicService;

    public SpringAIChatMemoryDefaultController(SpringAIChatMemoryService springAIBasicService) {
        this.springAIBasicService = springAIBasicService;
    }

    @GetMapping(value = "/chatmemory/ask")
    public ResponseEntity<String> getDefaultInformation(@RequestParam("question") Question question,@RequestHeader("userName") String userName){
        return springAIBasicService.getDefaultInformation(question,userName);
    }


}
