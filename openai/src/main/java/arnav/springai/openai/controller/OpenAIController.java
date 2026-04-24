package arnav.springai.openai.controller;

import arnav.springai.openai.model.Question;
import arnav.springai.openai.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
public class OpenAIController {

    private final OpenAIService openAIService;

    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping(value = "/ask")
    public String getInformation(@RequestBody Question question){
        return openAIService.getInformation(question);
    }

}
