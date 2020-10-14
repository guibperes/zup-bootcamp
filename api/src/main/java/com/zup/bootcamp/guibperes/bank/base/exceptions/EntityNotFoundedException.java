package com.zup.bootcamp.guibperes.bank.base.exceptions;

import com.zup.bootcamp.guibperes.bank.base.BaseException;

import org.springframework.http.HttpStatus;

public class EntityNotFoundedException extends BaseException {

  private static final long serialVersionUID = 1L;

  public EntityNotFoundedException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
