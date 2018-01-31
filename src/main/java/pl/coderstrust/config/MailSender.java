package pl.coderstrust.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailSender {

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
    emailSender.setHost("smtp.gmail.com");
    emailSender.setPort(587);

    return emailSender;
  }
}
