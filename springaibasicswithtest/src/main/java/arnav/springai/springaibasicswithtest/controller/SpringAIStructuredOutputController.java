package arnav.springai.springaibasicswithtest.controller;

import arnav.springai.springaibasicswithtest.model.CountryCities;
import arnav.springai.springaibasicswithtest.service.SpringAIStructuredOutputService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SpringAIStructuredOutputController {

    private final SpringAIStructuredOutputService springAIStructuredOutputService;

    public SpringAIStructuredOutputController(SpringAIStructuredOutputService springAIStructuredOutputService) {
        this.springAIStructuredOutputService = springAIStructuredOutputService;
    }

    @GetMapping("/chat-bean")
    public ResponseEntity<CountryCities> getCustomBeanFromChat(@RequestParam String question){
        return springAIStructuredOutputService.getCustomBean(question);
    }

    @GetMapping("/chat-list")
    public ResponseEntity<List<String>> getListFromChat(@RequestParam String question){
        return springAIStructuredOutputService.getList(question);
    }

    @GetMapping("/chat-map")
    public ResponseEntity<Map<String,Object>> getMapFromChat(@RequestParam String question){
        return springAIStructuredOutputService.getMap(question);
    }

    @GetMapping("/chat-bean-list")
    public ResponseEntity<List<CountryCities>> getListOfCustomBeanFromChat(@RequestParam String question){
        return springAIStructuredOutputService.getListOfCustomBean(question);
    }
}
