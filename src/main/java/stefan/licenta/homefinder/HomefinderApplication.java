package stefan.licenta.homefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import java.util.TimeZone;

@SpringBootApplication
@ComponentScan(basePackages = {"stefan.licenta.homefinder"})
@EnableJpaRepositories("stefan.licenta.homefinder")
@EntityScan("stefan.licenta.homefinder.entity")
public class HomefinderApplication {

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Bucharest"));
	}

	public static void main(String[] args) {
		SpringApplication.run(HomefinderApplication.class, args);
	}
}
