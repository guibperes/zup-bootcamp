package com.zup.bootcamp.guibperes.bank.api.image.dtos;

import org.springframework.core.io.Resource;

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
public class ImageDTO {

  private String type;
  private Resource resource;

  public static ImageDTO of(String type, Resource resource) {
    return ImageDTO.builder()
      .type(type)
      .resource(resource)
      .build();
  }
}
