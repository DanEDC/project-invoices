package pl.coderstrust;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Use this URL in a browser to run Swagger:
 * http://localhost:8080/swagger-ui.html#/
 */
@SpringBootApplication
@EnableScheduling
public class Application {
  
  private static Logger logger = LoggerFactory.getLogger(Application.class);
  
  public static void main(String[] args) {
    logger.info("Application initiated");
    SpringApplication.run(Application.class, args);
  }
}