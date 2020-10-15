package com.zup.bootcamp.guibperes.bank.base.exceptions;

import com.zup.bootcamp.guibperes.bank.base.BaseException;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends BaseException {

  private static final long serialVersionUID = 1L;

  public UnprocessableEntityException(String message) {
    super(HttpStatus.UNPROCESSABLE_ENTITY, message);
  }
}
