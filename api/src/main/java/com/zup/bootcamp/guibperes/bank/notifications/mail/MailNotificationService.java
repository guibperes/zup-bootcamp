package com.zup.bootcamp.guibperes.bank.notifications.mail;

import com.zup.bootcamp.guibperes.bank.configs.EnvironmentValues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailNotificationService {

  @Autowired
  private EnvironmentValues env;

  @Autowired
  private JavaMailSender mailSender;

  @Async
  public void sendEmailNotification(Mail mail) {
    try {
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(mail.getTo());
      mailMessage.setFrom(env.getMailFrom());
      mailMessage.setSubject(mail.getSubject());
      mailMessage.setText(mail.getContent());

      mailSender.send(mailMessage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
