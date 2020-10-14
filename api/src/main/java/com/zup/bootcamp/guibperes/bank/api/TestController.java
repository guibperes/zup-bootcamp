package com.zup.bootcamp.guibperes.bank.api;

import com.zup.bootcamp.guibperes.bank.base.annotations.RestConfig;
import com.zup.bootcamp.guibperes.bank.base.exceptions.BadRequestException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestConfig
public class TestController {

  @GetMapping
  public ResponseEntity<String> testException() {
    throw new BadRequestException("Deu ruim");
  }
}
