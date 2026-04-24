package arnav.springai.springaibasicswithtest.service;

import arnav.springai.springaibasicswithtest.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpringAIStructuredOutputServiceImpl implements SpringAIStructuredOutputService{

    private final ChatClient chatClient;

    public SpringAIStructuredOutputServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @Override
    public ResponseEntity<CountryCities> getCustomBean(String question) {
        CountryCities customBeanResponse = chatClient.prompt()
                .user(question)
                .call()
                .entity(CountryCities.class);
        return ResponseEntity.ok(customBeanResponse);
    }

    @Override
    public ResponseEntity<List<String>> getList(String question) {
        List<String> listResponse = chatClient.prompt()
                .user(question)
                .call()
                .entity(new ListOutputConverter());
        return ResponseEntity.ok(listResponse);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getMap(String question) {
        Map<String, Object> mapResponse = chatClient.prompt()
                .user(question)
                .call()
                .entity(new MapOutputConverter());
        return ResponseEntity.ok(mapResponse);
    }

    @Override
    public ResponseEntity<List<CountryCities>> getListOfCustomBean(String question) {
        List<CountryCities> listOfCustomBeanResponse = chatClient.prompt()
                .user(question)
                .call()
                .entity(new ParameterizedTypeReference<List<CountryCities>>() {
                });
        return ResponseEntity.ok(listOfCustomBeanResponse);
    }
}
