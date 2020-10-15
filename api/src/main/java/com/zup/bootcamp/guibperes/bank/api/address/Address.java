package com.zup.bootcamp.guibperes.bank.api.address;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.zup.bootcamp.guibperes.bank.api.address.dtos.AddressDTO;
import com.zup.bootcamp.guibperes.bank.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Address extends BaseEntity {

  @NotNull
  @Size(min = 8, max = 8)
  private String cep;

  @NotNull
  @Size(min = 3, max = 40)
  private String street;

  @NotNull
  @Size(min = 3, max = 40)
  private String district;

  @NotNull
  @Size(min = 3, max = 40)
  private String complement;

  @NotNull
  @Size(min = 3, max = 40)
  private String city;

  @NotNull
  @Size(min = 3, max = 40)
  private String state;

  public static Address of(AddressDTO dto) {
    return Address.builder()
      .cep(dto.getCep())
      .street(dto.getStreet())
      .district(dto.getDistrict())
      .complement(dto.getComplement())
      .city(dto.getCity())
      .state(dto.getState())
      .build();
  }
}
