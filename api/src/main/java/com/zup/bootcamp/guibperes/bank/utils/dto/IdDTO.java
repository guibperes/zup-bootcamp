package com.zup.bootcamp.guibperes.bank.utils.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IdDTO {

  private UUID id;

  public static IdDTO of(UUID id) {
    return new IdDTO(id);
  }
}
