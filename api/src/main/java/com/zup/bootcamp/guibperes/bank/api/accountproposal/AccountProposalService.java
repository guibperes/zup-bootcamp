package com.zup.bootcamp.guibperes.bank.api.accountproposal;

import java.util.UUID;

import com.zup.bootcamp.guibperes.bank.api.accountproposal.dtos.AccountProposalDTO;
import com.zup.bootcamp.guibperes.bank.api.address.Address;
import com.zup.bootcamp.guibperes.bank.api.address.dtos.AddressDTO;
import com.zup.bootcamp.guibperes.bank.api.image.Image;
import com.zup.bootcamp.guibperes.bank.base.annotations.TransactionalService;
import com.zup.bootcamp.guibperes.bank.base.dtos.IdDTO;
import com.zup.bootcamp.guibperes.bank.base.exceptions.BadRequestException;
import com.zup.bootcamp.guibperes.bank.base.exceptions.EntityNotFoundedException;
import com.zup.bootcamp.guibperes.bank.base.exceptions.UnprocessableEntityException;
import com.zup.bootcamp.guibperes.bank.base.storages.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@TransactionalService
public class AccountProposalService {

  @Autowired
  private AccountProposalRepository accountProposalRepository;

  @Autowired
  private StorageService storageService;

  private AccountProposal findAccountProposalById(UUID id) {
    return accountProposalRepository
      .findById(id)
      .orElseThrow(() -> new EntityNotFoundedException("Cannot find proposal with provided id"));
  }

  public IdDTO save(AccountProposalDTO accountProposalDTO) {
    if (!accountProposalDTO.isBirthDateValid()) {
      throw new BadRequestException("age must be at least 18");
    }

    var existsByEmail = accountProposalRepository.findByEmail(accountProposalDTO.getEmail());

    if (existsByEmail.isPresent()) {
      throw new BadRequestException("email already in use");
    }

    var existsByCpf = accountProposalRepository.findByCpf(accountProposalDTO.getCpf());

    if (existsByCpf.isPresent()) {
      throw new BadRequestException("cpf already in use");
    }

    var accountProposal = AccountProposal.of(accountProposalDTO);
    accountProposal.setIsInformationStepCompleted(true);

    var savedAccountProposal = accountProposalRepository.save(accountProposal);
    return IdDTO.of(savedAccountProposal.getId());
  }

  public IdDTO saveAddress(UUID proposalId, AddressDTO addressDTO) {
    var accountProposal = findAccountProposalById(proposalId);

    if (!accountProposal.getIsInformationStepCompleted()) {
      throw new UnprocessableEntityException("Step one is not completed");
    }

    var address = Address.of(addressDTO);
    accountProposal.setAddress(address);
    accountProposal.setIsAddressStepCompleted(true);

    var savedAccountProposal = accountProposalRepository.save(accountProposal);
    return IdDTO.of(savedAccountProposal.getId());
  }

  public IdDTO saveCpfImage(UUID proposalId, MultipartFile file) {
    var accountProposal = findAccountProposalById(proposalId);

    if (!accountProposal.getIsAddressStepCompleted()) {
      throw new UnprocessableEntityException("Step two is not completed");
    }

    var fileName = storageService.store(file, proposalId.toString());
    var image = Image.of(fileName, file.getContentType(), file.getSize());

    accountProposal.setCpfImage(image);
    accountProposal.setIsImageStepCompleted(true);

    var savedAccountProposal = accountProposalRepository.save(accountProposal);
    return IdDTO.of(savedAccountProposal.getId());
  }
}
