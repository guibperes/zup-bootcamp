package com.zup.bootcamp.guibperes.bank.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<BaseException> handleBaseException(BaseException exception) {
    return ResponseEntity
      .status(exception.getStatus())
      .body(exception);
  }
}
