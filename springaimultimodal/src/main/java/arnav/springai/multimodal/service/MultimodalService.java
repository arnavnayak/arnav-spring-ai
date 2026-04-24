package arnav.springai.multimodal.service;

import arnav.springai.multimodal.model.Question;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface MultimodalService {

    public String getInformation(Question question);

    public String getTranscription(Resource audioFile);

    public String getTranscriptionWithOptions(Resource audioFile);

    public String getSpeech(String message) throws IOException;

    public String getSpeechWithOptions(String message) throws IOException;

    public String getImage(String message);

    public String getImageWithOptions(String message);
}
