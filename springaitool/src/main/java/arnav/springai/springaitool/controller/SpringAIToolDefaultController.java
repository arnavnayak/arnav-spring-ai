package arnav.springai.springaitool.controller;

import arnav.springai.springaitool.model.Question;
import arnav.springai.springaitool.service.SpringAIToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringAIToolDefaultController {

    private final SpringAIToolService springAIToolService;

    public SpringAIToolDefaultController(SpringAIToolService springAIToolService) {
        this.springAIToolService = springAIToolService;
    }

    @GetMapping(value = "/tool/local-time/ask")
    public ResponseEntity<String> localTime(@RequestParam("question") String question,@RequestHeader("userName") String userName){
        return springAIToolService.getLocalTime(question,userName);
    }

}
