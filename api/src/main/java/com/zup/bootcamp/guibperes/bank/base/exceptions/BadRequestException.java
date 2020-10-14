package com.zup.bootcamp.guibperes.bank.base.exceptions;

import com.zup.bootcamp.guibperes.bank.base.BaseException;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

  private static final long serialVersionUID = 1L;

  public BadRequestException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
