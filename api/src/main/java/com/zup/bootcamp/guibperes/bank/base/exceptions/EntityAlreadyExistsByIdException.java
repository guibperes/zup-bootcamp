package com.zup.bootcamp.guibperes.bank.base.exceptions;

import com.zup.bootcamp.guibperes.bank.base.BaseException;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsByIdException extends BaseException {

  private static final long serialVersionUID = 1L;

  public EntityAlreadyExistsByIdException(String message) {
    super(HttpStatus.CONFLICT, message);
  }
}
