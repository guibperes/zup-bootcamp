package com.zup.bootcamp.guibperes.bank.api.accountproposal;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import com.zup.bootcamp.guibperes.bank.api.accountproposal.dto.AccountProposalStepOneDTO;
import com.zup.bootcamp.guibperes.bank.api.accountproposal.dto.AccountProposalStepTwoDTO;
import com.zup.bootcamp.guibperes.bank.base.annotations.TransactionalService;
import com.zup.bootcamp.guibperes.bank.base.exceptions.BadRequestException;
import com.zup.bootcamp.guibperes.bank.base.exceptions.EntityNotFoundedException;
import com.zup.bootcamp.guibperes.bank.base.exceptions.InternalServerError;
import com.zup.bootcamp.guibperes.bank.base.exceptions.UnprocessableEntityException;
import com.zup.bootcamp.guibperes.bank.configs.EnvironmentValues;
import com.zup.bootcamp.guibperes.bank.utils.dto.IdDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@TransactionalService
public class AccountProposalService {

  @Autowired
  private AccountProposalRepository accountProposalRepository;

  @Autowired
  private EnvironmentValues env;

  private AccountProposal findAccountProposalById(UUID id) {
    return accountProposalRepository
      .findById(id)
      .orElseThrow(() -> new EntityNotFoundedException("Cannot find proposal with provided id"));
  }

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

  public IdDTO stepTwo(UUID proposalId, AccountProposalStepTwoDTO accountProposalStepTwoDTO) {
    var accountProposal = findAccountProposalById(proposalId);

    if (!accountProposal.getIsStepOneCompleted()) {
      throw new UnprocessableEntityException("Step one is not completed");
    }

    accountProposal.mergeFromDTO(accountProposalStepTwoDTO);

    var savedAccountProposal = accountProposalRepository.save(accountProposal);

    return IdDTO.of(savedAccountProposal.getId());
  }

  public IdDTO stepThree(UUID proposalId, MultipartFile file) {
    try (InputStream fileStream = file.getInputStream()) {
      Files.copy(
        fileStream,
        Path.of(env.getImagesFolder(), file.getOriginalFilename()),
        StandardCopyOption.REPLACE_EXISTING
      );
    } catch (Exception e) {
      e.printStackTrace();
      throw new InternalServerError("Cannot save image on filesytem");
    }

    return IdDTO.of(proposalId);
  }
}
