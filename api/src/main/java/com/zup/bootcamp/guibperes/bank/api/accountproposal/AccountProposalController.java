package com.zup.bootcamp.guibperes.bank.api.accountproposal;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import com.zup.bootcamp.guibperes.bank.api.accountproposal.dtos.AccountProposalStepOneDTO;
import com.zup.bootcamp.guibperes.bank.api.accountproposal.dtos.AccountProposalStepTwoDTO;
import com.zup.bootcamp.guibperes.bank.base.annotations.RestConfig;
import com.zup.bootcamp.guibperes.bank.base.exceptions.BadRequestException;
import com.zup.bootcamp.guibperes.bank.configs.EnvironmentValues;

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

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@RestConfig
@Api(tags = "AccountProposal")
@RequestMapping("/accountproposals")
public class AccountProposalController {

  @Autowired
  private AccountProposalService accountProposalService;

  @Autowired
  private EnvironmentValues env;

  private final String controllerPath = "/accountproposals";

  @PostMapping
  public ResponseEntity<Void> stepOne(
    @RequestBody @Valid AccountProposalStepOneDTO accountProposalStepOneDTO,
    @ApiIgnore Errors errors
  ) {
    if (errors.hasErrors()) {
      throw new BadRequestException(errors.getFieldErrors());
    }

    var id = accountProposalService.stepOne(accountProposalStepOneDTO);

    var locationHeader = new StringBuilder()
      .append(env.getApplicationUrl())
      .append(controllerPath)
      .append("/")
      .append(id.getId())
      .append("/steptwo")
      .toString();

    return ResponseEntity
      .created(URI.create(locationHeader))
      .build();
  }

  @PatchMapping("/{proposalId}/steptwo")
  public ResponseEntity<Void> stepTwo(
    @PathVariable UUID proposalId,
    @RequestBody @Valid AccountProposalStepTwoDTO accountProposalStepTwoDTO,
    @ApiIgnore Errors errors
  ) {
    if (errors.hasErrors()) {
      throw new BadRequestException(errors.getFieldErrors());
    }

    var id = accountProposalService.stepTwo(proposalId, accountProposalStepTwoDTO);

    var locationHeader = new StringBuilder()
      .append(env.getApplicationUrl())
      .append(controllerPath)
      .append("/")
      .append(id.getId())
      .append("/stepthree")
      .toString();

    return ResponseEntity
      .created(URI.create(locationHeader))
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

    var id = accountProposalService.stepThree(proposalId, file);

    return ResponseEntity
      .ok()
      .build();
  }
}
