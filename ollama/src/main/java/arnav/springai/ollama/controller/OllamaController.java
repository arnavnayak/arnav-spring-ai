package arnav.springai.ollama.controller;

import arnav.springai.ollama.model.Question;
import arnav.springai.ollama.service.OllamaService;
import org.springframework.web.bind.annotation.*;

@RestController
public class OllamaController {

    private final OllamaService ollamaService;

    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping(value = "/ask")
    public String getInformation(@RequestBody Question question){
        return ollamaService.getInformation(question);
    }

}
