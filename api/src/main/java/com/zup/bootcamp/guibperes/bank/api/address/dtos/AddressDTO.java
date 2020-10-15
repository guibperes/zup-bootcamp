package com.zup.bootcamp.guibperes.bank.api.address.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class AddressDTO {

  @NotBlank
  @Size(min = 8, max = 8)
  private String cep;

  @NotBlank
  @Size(min = 3, max = 40)
  private String street;

  @NotBlank
  @Size(min = 3, max = 40)
  private String district;

  @NotBlank
  @Size(min = 3, max = 40)
  private String complement;

  @NotBlank
  @Size(min = 3, max = 40)
  private String city;

  @NotBlank
  @Size(min = 3, max = 40)
  private String state;
}
