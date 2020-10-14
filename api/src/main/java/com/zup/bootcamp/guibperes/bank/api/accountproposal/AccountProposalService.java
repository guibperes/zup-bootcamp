package com.zup.bootcamp.guibperes.bank.api.accountproposal;

import com.zup.bootcamp.guibperes.bank.api.accountproposal.dto.AccountProposalStepOneDTO;
import com.zup.bootcamp.guibperes.bank.base.annotations.TransactionalService;
import com.zup.bootcamp.guibperes.bank.base.exceptions.BadRequestException;
import com.zup.bootcamp.guibperes.bank.utils.dto.IdDTO;

import org.springframework.beans.factory.annotation.Autowired;

@TransactionalService
public class AccountProposalService {

  @Autowired
  private AccountProposalRepository accountProposalRepository;

  public IdDTO stepOne(AccountProposalStepOneDTO accountProposalStepOneDTO) {
    if (!accountProposalStepOneDTO.isBirthDateValid()) {
      throw new BadRequestException("age must be at least 18");
    }

    var existsByEmail = accountProposalRepository.findByEmail(accountProposalStepOneDTO.getEmail());

    if (existsByEmail.isPresent()) {
      throw new BadRequestException("email already in use");
    }

    var existsByCpf = accountProposalRepository.findByCpf(accountProposalStepOneDTO.getCpf());

    if (existsByCpf.isPresent()) {
      throw new BadRequestException("cpf already in use");
    }

    var accountProposal = new AccountProposal();
    accountProposal.mergeFromDTO(accountProposalStepOneDTO);

    var savedAccountProposal = accountProposalRepository.save(accountProposal);

    return IdDTO.of(savedAccountProposal.getId());
  }
}
