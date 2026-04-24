package arnav.springai.springaibasics.controller;

import arnav.springai.springaibasics.model.Question;
import arnav.springai.springaibasics.service.SpringAIBasicService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
public class SpringAIBasicDefaultController {

    private final SpringAIBasicService springAIBasicService;

    public SpringAIBasicDefaultController(SpringAIBasicService springAIBasicService) {
        this.springAIBasicService = springAIBasicService;
    }

    @GetMapping(value = "/default/ask")
    public String getDefaultInformation(){
        return springAIBasicService.getDefaultInformation();
    }

    @GetMapping(value = "/advisor/ask")
    public String getDefaultInformationWithAdvisor(){
        return springAIBasicService.getDefaultInformationWithAdvisor();
    }

    @GetMapping(value = "/stream")
    public Flux<String> getDefaultInformationStream(){
        return springAIBasicService.getDefaultInformationStream();
    }

    @PostMapping(value = "/ask")
    public String getDefaultInformationBasedOnQuestionAsked(@RequestBody Question question){
        return springAIBasicService.getDefaultInformationBasedOnQuestionAsked(question);
    }

    @PostMapping(value = "/promptstuffed/ask")
    public String getPromptStuffedInformationBasedOnQuestionAsked(@RequestBody Question question){
        return springAIBasicService.getPromptStuffedInformationBasedOnQuestionAsked(question);
    }

    @GetMapping(value = "/email")
    public String getEmailReply(@RequestParam("customerName") String customerName,
                                @RequestParam("customerMessage") String customerMessage){
        return springAIBasicService.getEmailReply(customerName,customerMessage);
    }


}
