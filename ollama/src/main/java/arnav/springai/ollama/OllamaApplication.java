package arnav.springai.ollama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class OllamaApplication {

	public static void main(String[] args) {
		SpringApplication.run(OllamaApplication.class, args);
	}

}
