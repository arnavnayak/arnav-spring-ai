package arnav.springai.multimodal.service;

import arnav.springai.multimodal.model.Question;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MultimodalServiceImpl implements MultimodalService {

    private final ChatClient chatClient;

    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;
    private final OpenAiImageModel openAiImageModel;

    public MultimodalServiceImpl(ChatClient.Builder chatClientBuilder,OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel,
                                  OpenAiAudioSpeechModel openAiAudioSpeechModel,
                                 OpenAiImageModel openAiImageModel) {
        this.chatClient = chatClientBuilder
                .build();
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
        this.openAiAudioSpeechModel = openAiAudioSpeechModel;
        this.openAiImageModel = openAiImageModel;
    }


    @Override
    public String getInformation(Question question) {

        UserMessage userMessage = UserMessage.builder()
                .text(question.question())
                .build();
        Prompt prompt = Prompt.builder()
                .messages(userMessage)
                .build();

        return chatClient.prompt(prompt).call().content();
    }

    @Override
    public String getTranscription(Resource audioFile) {
        return openAiAudioTranscriptionModel.call(audioFile);
    }

    @Override
    public String getTranscriptionWithOptions(Resource audioFile) {
        var response = openAiAudioTranscriptionModel.call(new AudioTranscriptionPrompt(audioFile,
                OpenAiAudioTranscriptionOptions.builder()
                        .prompt("Talking about Spring AI")
                        .language("en")
                        .temperature(0.5f)
                        .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.SRT)
                .build()));

        return response.getResult().getOutput();
    }

    @Override
    public String getSpeech(String message) throws IOException {
        byte[] audioBytes = openAiAudioSpeechModel.call(message);
        Path path = Paths.get("output.mp3");
        Files.write(path, audioBytes);
        return "Mp3 saved successfully to path :" + path.toAbsolutePath();
    }

    @Override
    public String getSpeechWithOptions(String message) throws IOException {
        TextToSpeechResponse speechResponse = openAiAudioSpeechModel.call(new TextToSpeechPrompt(message,
                OpenAiAudioSpeechOptions.builder()
                        .voice(OpenAiAudioApi.SpeechRequest.Voice.NOVA)
                        .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                        .speed(0.5)
                .build()));
        Path path = Paths.get("output.mp3");
        Files.write(path, speechResponse.getResult().getOutput());
        return "Mp3 saved successfully to path :" + path.toAbsolutePath();
    }

    @Override
    public String getImage(String message) {
        return openAiImageModel.call(new ImagePrompt(message)).getResult().getOutput().getUrl();
    }

    @Override
    public String getImageWithOptions(String message) {
        return openAiImageModel.call(new ImagePrompt(message,OpenAiImageOptions.builder()
                .style("vivid")
                .quality("hd")
                .N(1)
                .width(1024)
                .height(1024)
                .responseFormat("url")
                .build())).getResult().getOutput().getUrl();
    }
}
