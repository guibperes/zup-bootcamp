package com.zup.bootcamp.guibperes.bank.api.accountproposal.dto;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.caelum.stella.bean.validation.CPF;
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
public class AccountProposalStepOneDTO {

  @NotBlank
  @Size(min = 3, max = 40)
  private String firstName;

  @NotBlank
  @Size(min = 3, max = 40)
  private String lastName;

  @Email
  @NotBlank
  @Size(min = 3, max = 80)
  private String email;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @NotBlank
  @CPF
  @Size(min = 11, max = 11)
  private String cpf;

  public boolean isBirthDateValid() {
    return Period.between(this.birthDate, LocalDate.now()).getYears() >= 18;
  }
}
