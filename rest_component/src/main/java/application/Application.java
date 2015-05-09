package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@ImportResource("classpath:spring-security.xml")
public class Application {
	
	 public static void main(String[] args) {
		 SpringApplication app = new SpringApplication(Application.class);
		 app.setWebEnvironment(false);
	     app.run(args);
     }
}
