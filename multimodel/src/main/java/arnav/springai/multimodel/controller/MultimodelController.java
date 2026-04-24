package arnav.springai.multimodel.controller;

import arnav.springai.multimodel.model.Question;
import arnav.springai.multimodel.service.MultimodelService;
import org.springframework.web.bind.annotation.*;

@RestController
public class MultimodelController {

    private final MultimodelService multimodelService;

    public MultimodelController(MultimodelService multimodelService) {
        this.multimodelService = multimodelService;
    }

    @PostMapping(value = "/openAI/ask")
    public String getInformationFromOpenAI(@RequestBody Question question){
        return multimodelService.getInformationFromOpenAI(question);
    }

    @PostMapping(value = "/ollama/ask")
    public String getInformationFromOllama(@RequestBody Question question){
        return multimodelService.getInformationFromOllama(question);
    }

}
