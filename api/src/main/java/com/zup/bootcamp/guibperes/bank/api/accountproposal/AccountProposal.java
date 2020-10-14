package com.zup.bootcamp.guibperes.bank.api.accountproposal;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zup.bootcamp.guibperes.bank.api.accountproposal.dto.AccountProposalStepOneDTO;
import com.zup.bootcamp.guibperes.bank.base.BaseEntity;

import br.com.caelum.stella.bean.validation.CPF;
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

  @NotBlank
  @Size(min = 3, max = 40)
  private String firstName;

  @NotBlank
  @Size(min = 3, max = 40)
  private String lastName;

  @Email
  @NotBlank
  @Size(min = 3, max = 80)
  @Column(unique = true)
  private String email;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @NotBlank
  @CPF
  @Size(min = 11, max = 11)
  @Column(unique = true)
  private String cpf;

  public void mergeFromDTO(AccountProposalStepOneDTO dto) {
    this.firstName = dto.getFirstName();
    this.lastName = dto.getLastName();
    this.email = dto.getEmail();
    this.birthDate = dto.getBirthDate();
    this.cpf = dto.getCpf();
  }
}
