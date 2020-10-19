package com.zup.bootcamp.guibperes.bank.notifications.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Mail {

  private String to;
  private String subject;
  private String content;

  public static Mail of(String to, String subject, String content) {
    return Mail.builder()
      .to(to)
      .subject(subject)
      .content(content)
      .build();
  }
}
