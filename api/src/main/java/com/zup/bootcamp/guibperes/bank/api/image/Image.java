package com.zup.bootcamp.guibperes.bank.api.image;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.zup.bootcamp.guibperes.bank.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Image extends BaseEntity {

  @NotNull
  @NotBlank
  @Size(max = 80)
  private String name;

  @NotNull
  @NotBlank
  @Size(max = 80)
  private String type;

  @NotNull
  private Long size;

  @Transient
  @Setter
  private String url;

  public static Image of(String name, String type, Long size) {
    return Image.builder()
      .name(name)
      .type(type)
      .size(size)
      .build();
  }
}
