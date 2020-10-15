package com.zup.bootcamp.guibperes.bank.api.accountproposal;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zup.bootcamp.guibperes.bank.api.accountproposal.dto.AccountProposalStepOneDTO;
import com.zup.bootcamp.guibperes.bank.api.accountproposal.dto.AccountProposalStepTwoDTO;
import com.zup.bootcamp.guibperes.bank.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class AccountProposal extends BaseEntity {

  @Size(min = 3, max = 40)
  private String firstName;

  @Size(min = 3, max = 40)
  private String lastName;

  @Size(min = 3, max = 80)
  @Column(unique = true)
  private String email;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @Size(min = 11, max = 11)
  @Column(unique = true)
  private String cpf;

  @Size(min = 8, max = 8)
  private String cep;

  @Size(min = 3, max = 40)
  private String street;

  @Size(min = 3, max = 40)
  private String district;

  @Size(min = 3, max = 40)
  private String complement;

  @Size(min = 3, max = 40)
  private String city;

  @Size(min = 3, max = 40)
  private String state;

  @NotNull
  private Boolean isStepOneCompleted = false;

  @NotNull
  private Boolean isStepTwoCompleted = false;

  public void mergeFromDTO(AccountProposalStepOneDTO dto) {
    this.firstName = dto.getFirstName();
    this.lastName = dto.getLastName();
    this.email = dto.getEmail();
    this.birthDate = dto.getBirthDate();
    this.cpf = dto.getCpf();

    this.isStepOneCompleted = true;
  }

  public void mergeFromDTO(AccountProposalStepTwoDTO dto) {
    this.cep = dto.getCep();
    this.street = dto.getStreet();
    this.district = dto.getDistrict();
    this.complement = dto.getComplement();
    this.city = dto.getCity();
    this.state = dto.getState();

    this.isStepTwoCompleted = true;
  }
}
