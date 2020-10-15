package com.zup.bootcamp.guibperes.bank.api.accountproposal;

import java.util.UUID;

import javax.validation.Valid;

import com.zup.bootcamp.guibperes.bank.api.accountproposal.dtos.AccountProposalDTO;
import com.zup.bootcamp.guibperes.bank.api.address.dtos.AddressDTO;
import com.zup.bootcamp.guibperes.bank.base.annotations.RestConfig;
import com.zup.bootcamp.guibperes.bank.base.exceptions.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@RestConfig
@Api(tags = "AccountProposal")
@RequestMapping("/accountproposals")
public class AccountProposalController {

  @Autowired
  private AccountProposalService accountProposalService;

  @PostMapping
  public ResponseEntity<Void> save(
    @RequestBody @Valid AccountProposalDTO accountProposalDTO,
    @ApiIgnore Errors errors
  ) {
    if (errors.hasErrors()) {
      throw new BadRequestException(errors.getFieldErrors());
    }

    var id = accountProposalService.save(accountProposalDTO);

    var location = ServletUriComponentsBuilder.fromCurrentContextPath()
      .path("/accountproposals")
      .path(id.getId().toString())
      .path("/address")
      .build()
      .toUri();

    return ResponseEntity
      .created(location)
      .build();
  }

  @PatchMapping("/{proposalId}/address")
  public ResponseEntity<Void> saveAddress(
    @PathVariable UUID proposalId,
    @RequestBody @Valid AddressDTO addressDTO,
    @ApiIgnore Errors errors
  ) {
    if (errors.hasErrors()) {
      throw new BadRequestException(errors.getFieldErrors());
    }

    var id = accountProposalService.saveAddress(proposalId, addressDTO);

    var location = ServletUriComponentsBuilder.fromCurrentContextPath()
      .path("/accountproposals")
      .path(id.getId().toString())
      .path("/stepthree")
      .build()
      .toUri();

    return ResponseEntity
      .created(location)
      .build();
  }

  @PatchMapping("/{proposalId}/stepthree")
  public ResponseEntity<Void> stepThree(
    @PathVariable UUID proposalId,
    @RequestPart("file") MultipartFile file
  ) {
    if (file.isEmpty()) {
      throw new BadRequestException("Image file is required");
    }

    if (!file.getContentType().equals("image/jpeg")) {
      throw new BadRequestException("Image file must be JPEG type");
    }

    accountProposalService.stepThree(proposalId, file);

    return ResponseEntity
      .ok()
      .build();
  }
}
