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

//    emailSender.setUsername("marcin.martyna1979@gmail.com");
//    emailSender.setPassword("Java79mar");

//    Properties props = emailSender.getJavaMailProperties();
//    props.put("mail.transport.protocol", "smtp");
//    props.put("mail.smtp.auth", "true");
//    props.put("mail.smtp.starttls.enable", "true");
 //   props.put("mail.debug", "true");

    return emailSender;
  }
}
