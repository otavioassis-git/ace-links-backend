package api.ace_links;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AceLinksApplication {

	public static void main(String[] args) {
		SpringApplication.run(AceLinksApplication.class, args);
	}

}
