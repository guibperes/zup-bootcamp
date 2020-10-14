package com.zup.bootcamp.guibperes.bank.api.accountproposal;

import javax.validation.Valid;

import com.zup.bootcamp.guibperes.bank.api.accountproposal.dto.AccountProposalStepOneDTO;
import com.zup.bootcamp.guibperes.bank.base.annotations.RestConfig;
import com.zup.bootcamp.guibperes.bank.base.exceptions.BadRequestException;
import com.zup.bootcamp.guibperes.bank.configs.EnvironmentConfig.EnvironmentVariables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@RestConfig
@Api(tags = "AccountProposal")
@RequestMapping("/accountproposals")
public class AccountProposalController {

  @Autowired
  private AccountProposalService accountProposalService;

  @Autowired
  private EnvironmentVariables envVariables;

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
      .append(envVariables.getApplicationUrl())
      .append(controllerPath)
      .append("/")
      .append(id.getId())
      .append("/steptwo")
      .toString();

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .header("Location", locationHeader)
      .build();
  }
}
