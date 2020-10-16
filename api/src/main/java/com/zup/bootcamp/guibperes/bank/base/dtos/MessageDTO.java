package com.zup.bootcamp.guibperes.bank.base.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class MessageDTO {

  private String message;

  public static MessageDTO of(String message) {
    return MessageDTO.builder()
      .message(message)
      .build();
  }
}
