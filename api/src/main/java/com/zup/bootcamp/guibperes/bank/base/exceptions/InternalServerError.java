package com.zup.bootcamp.guibperes.bank.base.exceptions;

import com.zup.bootcamp.guibperes.bank.base.BaseException;

import org.springframework.http.HttpStatus;

public class InternalServerError extends BaseException {

  private static final long serialVersionUID = 1L;

  public InternalServerError(String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }
}
