package arnav.springai.multimodal.controller;

import arnav.springai.multimodal.model.Question;
import arnav.springai.multimodal.service.MultimodalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class MultimodalController {

    private final MultimodalService multimodalService;

    public MultimodalController(MultimodalService multimodalService) {
        this.multimodalService = multimodalService;
    }

    @PostMapping(value = "/multimodal/ask")
    public String getInformation(@RequestBody Question question){
        return multimodalService.getInformation(question);
    }

    @GetMapping(value = "/multimodal/transcribe")
    public String transcribe(@Value("classpath:SpringAI.mp3")Resource audioFile){
        return multimodalService.getTranscription(audioFile);
    }

    @GetMapping(value = "/multimodal/transcribe-options")
    public String transcribeWithOptions(@Value("classpath:SpringAI.mp3")Resource audioFile){
        return multimodalService.getTranscriptionWithOptions(audioFile);
    }

    @GetMapping(value = "/multimodal/speech")
    public String speech(@RequestParam("message")String message) throws IOException {
        return multimodalService.getSpeech(message);
    }

    @GetMapping(value = "/multimodal/speech-options")
    public String speechWithOptions(@RequestParam("message")String message) throws IOException {
        return multimodalService.getSpeechWithOptions(message);
    }

    @GetMapping(value = "/multimodal/image")
    public String image(@RequestParam("message")String message) throws IOException {
        return multimodalService.getImage(message);
    }

    @GetMapping(value = "/multimodal/image-options")
    public String imageWithOptions(@RequestParam("message")String message) throws IOException {
        return multimodalService.getImageWithOptions(message);
    }

}
