package arnav.springai.springaibasicswithtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableAutoConfiguration
@EnableRetry
public class SpringAIBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAIBasicApplication.class, args);
	}

}
