package com.zup.bootcamp.guibperes.bank.api.accountproposal;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zup.bootcamp.guibperes.bank.api.accountproposal.dtos.AccountProposalDTO;
import com.zup.bootcamp.guibperes.bank.api.address.Address;
import com.zup.bootcamp.guibperes.bank.api.image.Image;
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
public class AccountProposal extends BaseEntity {

  @NotBlank
  @NotNull
  @Size(min = 3, max = 40)
  private String firstName;

  @NotBlank
  @NotNull
  @Size(min = 3, max = 40)
  private String lastName;

  @NotBlank
  @NotNull
  @Size(min = 3, max = 80)
  @Column(unique = true)
  private String email;

  @NotBlank
  @NotNull
  @Size(min = 11, max = 11)
  @Column(unique = true)
  private String cpf;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @Setter
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "address_id")
  private Address address;

  @Setter
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "cpf_image_id")
  private Image cpfImage;

  @NotNull
  @Builder.Default
  @Setter
  @JsonIgnore
  private Boolean isInformationStepCompleted = false;

  @NotNull
  @Builder.Default
  @Setter
  @JsonIgnore
  private Boolean isAddressStepCompleted = false;

  @NotNull
  @Builder.Default
  @Setter
  @JsonIgnore
  private Boolean isImageStepCompleted = false;

  public static AccountProposal of(AccountProposalDTO dto) {
    return AccountProposal.builder()
      .firstName(dto.getFirstName())
      .lastName(dto.getLastName())
      .email(dto.getEmail())
      .cpf(dto.getCpf())
      .birthDate(dto.getBirthDate())
      .build();
  }
}
