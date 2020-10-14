package com.zup.bootcamp.guibperes.bank.base.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import com.zup.bootcamp.guibperes.bank.base.BaseException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

public class BadRequestException extends BaseException {

  private static final long serialVersionUID = 1L;

  public BadRequestException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }

  public BadRequestException(List<FieldError> errors) {
    super(HttpStatus.BAD_REQUEST, fieldErrorsToString(errors));
  }

  private static String fieldErrorsToString(List<FieldError> errors) {
    return errors.stream()
      .map(error -> error.getField() + " " + error.getDefaultMessage())
      .collect(Collectors.joining(", "));
  }
}
