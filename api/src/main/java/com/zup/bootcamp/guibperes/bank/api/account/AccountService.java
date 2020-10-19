package com.zup.bootcamp.guibperes.bank.api.account;

import com.zup.bootcamp.guibperes.bank.api.accountproposal.AccountProposal;
import com.zup.bootcamp.guibperes.bank.base.annotations.TransactionalService;

import org.springframework.scheduling.annotation.Async;

@TransactionalService
public class AccountService {

  @Async
  public void asyncCreate(AccountProposal accountProposal) {
    System.out.println("accout create process...");
  }
}
