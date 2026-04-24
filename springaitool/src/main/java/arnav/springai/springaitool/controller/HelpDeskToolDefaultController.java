package arnav.springai.springaitool.controller;

import arnav.springai.springaitool.model.Question;
import arnav.springai.springaitool.service.HelpDeskToolService;
import arnav.springai.springaitool.tool.HelpDeskTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
public class HelpDeskToolDefaultController {

    private final HelpDeskTools helpDeskTools;
    private final ChatClient chatClient;

    public HelpDeskToolDefaultController(@Qualifier("helpDeskChatClient")ChatClient chatClient, HelpDeskTools helpDeskTools) {
        this.chatClient = chatClient;
        this.helpDeskTools = helpDeskTools;
    }

    @GetMapping(value = "/tool/help-desk/ask")
    public ResponseEntity<String> localTime(@RequestParam("question") String question,@RequestHeader("username") String username){
        String result = chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID,username))
                .user(question)
                .tools(helpDeskTools)
                .toolContext(Map.of("username", username))
                .call()
                .content();
        return ResponseEntity.ok(result);
    }

}
