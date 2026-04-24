package arnav.springai.springaibasics.service;

import arnav.springai.springaibasics.model.CountryCities;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SpringAIStructuredOutputService {

    public ResponseEntity<CountryCities> getCustomBean(String question);

    public ResponseEntity<List<String>> getList(String question);

    public ResponseEntity<Map<String,Object>> getMap(String question);

    public ResponseEntity<List<CountryCities>> getListOfCustomBean(String question);
}
