package arnav.springai.springaimcpclient.controller;

import arnav.springai.springaimcpclient.model.Question;
import arnav.springai.springaimcpclient.service.SpringAIMCPClientService;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpringAIMCPClientController {

    private final SpringAIMCPClientService springAIMCPClientService;

    public SpringAIMCPClientController(SpringAIMCPClientService springAIMCPClientService) {
        this.springAIMCPClientService = springAIMCPClientService;
    }

    @GetMapping(value = "/ask")
    public String getInformation(@RequestHeader(value = "username", required = false) String username,@RequestParam Question question){
        return springAIMCPClientService.getInformation(username,question);
    }

}
