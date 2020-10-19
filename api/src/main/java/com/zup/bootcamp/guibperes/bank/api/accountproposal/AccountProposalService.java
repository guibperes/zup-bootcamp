package com.zup.bootcamp.guibperes.bank.api.accountproposal;

import java.util.Optional;
import java.util.UUID;

import com.zup.bootcamp.guibperes.bank.api.account.AccountService;
import com.zup.bootcamp.guibperes.bank.api.accountproposal.dtos.AccountProposalDTO;
import com.zup.bootcamp.guibperes.bank.api.address.Address;
import com.zup.bootcamp.guibperes.bank.api.address.AddressService;
import com.zup.bootcamp.guibperes.bank.api.address.dtos.AddressDTO;
import com.zup.bootcamp.guibperes.bank.api.image.ImageService;
import com.zup.bootcamp.guibperes.bank.base.annotations.TransactionalService;
import com.zup.bootcamp.guibperes.bank.base.dtos.IdDTO;
import com.zup.bootcamp.guibperes.bank.base.dtos.MessageDTO;
import com.zup.bootcamp.guibperes.bank.base.exceptions.BadRequestException;
import com.zup.bootcamp.guibperes.bank.base.exceptions.EntityNotFoundedException;
import com.zup.bootcamp.guibperes.bank.base.exceptions.UnprocessableEntityException;
import com.zup.bootcamp.guibperes.bank.notifications.mail.Mail;
import com.zup.bootcamp.guibperes.bank.notifications.mail.MailNotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@TransactionalService
public class AccountProposalService {

  @Autowired
  private AccountProposalRepository accountProposalRepository;

  @Autowired
  private AddressService addressService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private MailNotificationService mailService;

  @Autowired
  private AccountService accountService;

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

    var addressOptional = Optional.ofNullable(accountProposal.getAddress());

    if (addressOptional.isPresent()) {
      addressService.deleteById(addressOptional.get().getId());
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

    var imageOptional = Optional.ofNullable(accountProposal.getCpfImage());

    if (imageOptional.isPresent()) {
      imageService.deleteById(imageOptional.get().getId());
    }

    var image = imageService.save(file, proposalId);

    accountProposal.setCpfImage(image);
    accountProposal.setIsImageStepCompleted(true);

    var savedAccountProposal = accountProposalRepository.save(accountProposal);
    return IdDTO.of(savedAccountProposal.getId());
  }

  public AccountProposal findById(UUID id) {
    var accountProposal = findAccountProposalById(id);
    var imageOptional = Optional.ofNullable(accountProposal.getCpfImage());

    if (imageOptional.isPresent()) {
      var imageUrl = ServletUriComponentsBuilder
      .fromCurrentContextPath()
      .path("/images/")
      .path(imageOptional.get().getId().toString())
      .build()
      .toString();

      accountProposal.getCpfImage().setUrl(imageUrl);
    }

    return accountProposal;
  }

  public MessageDTO proposalAcceptCheck(UUID proposalId, Boolean isProposalAccepted) {
    var accountProposal = findAccountProposalById(proposalId);

    if (
      !accountProposal.getIsInformationStepCompleted() ||
      !accountProposal.getIsAddressStepCompleted() ||
      !accountProposal.getIsImageStepCompleted()
    ) {
      throw new UnprocessableEntityException("All steps must be completed");
    }

    var isProposalAcceptedOptional = Optional.ofNullable(accountProposal.getIsAccepted());

    if (isProposalAcceptedOptional.isPresent()) {
      var responseMessage = isProposalAcceptedOptional.get()
        ? "Proposal already accepted"
        : "Proposal already rejected";

      return MessageDTO.of(responseMessage);
    }

    accountProposal.setIsAccepted(isProposalAccepted);
    accountProposalRepository.save(accountProposal);

    var mail = Mail.of(
      accountProposal.getEmail(),
      "ZUP Bank - Account Proposal",
      isProposalAccepted
        ? "Thanks for the preference, your account will be created. This process may take a while."
        : "Please accept our proposal and we will give you a big discount."
    );
    mailService.sendEmailNotification(mail);

    if(isProposalAccepted) {
      accountService.asyncCreate(accountProposal);
    }

    return MessageDTO.of(
      isProposalAccepted
        ? "Your new account will be created and we will send you an email"
        : "We will send you an email begging you to accept our proposal"
    );
  }
}
