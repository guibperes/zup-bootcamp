package com.zup.bootcamp.guibperes.bank.api.account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zup.bootcamp.guibperes.bank.api.accountproposal.AccountProposal;
import com.zup.bootcamp.guibperes.bank.api.address.Address;
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
public class Account extends BaseEntity {

  @NotBlank
  @NotNull
  @Size(min = 4, max = 4)
  private String agency;

  @NotBlank
  @NotNull
  @Size(min = 8, max = 8)
  private String number;

  @NotBlank
  @NotNull
  @Size(min = 3, max = 3)
  private String bankCode;

  @NotNull
  @Builder.Default
  @Min(0)
  private BigDecimal balance = BigDecimal.ZERO;

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
  @NotNull
  @OneToOne
  @JoinColumn(name = "account_proposal_id")
  @JsonIgnore
  private AccountProposal accountProposal;

  public static Account of(AccountProposal accountProposal) {
    var generator = new Random();

    var agency = String.valueOf(generator.nextInt(9999 - 1000) + 1000);
    var number = String.valueOf(generator.nextInt(99999999 - 10000000) + 10000000);
    var bankCode = "001";

    return Account.builder()
      .agency(agency)
      .number(number)
      .bankCode(bankCode)
      .firstName(accountProposal.getFirstName())
      .lastName(accountProposal.getLastName())
      .email(accountProposal.getEmail())
      .cpf(accountProposal.getCpf())
      .birthDate(accountProposal.getBirthDate())
      .address(accountProposal.getAddress())
      .build();
  }
}
