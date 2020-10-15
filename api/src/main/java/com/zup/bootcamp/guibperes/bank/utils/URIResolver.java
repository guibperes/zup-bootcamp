package com.zup.bootcamp.guibperes.bank.utils;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.zup.bootcamp.guibperes.bank.configs.EnvironmentValues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class URIResolver {

  @Autowired
  private EnvironmentValues env;

  public URI resolve(List<String> paths) {
    var path = paths.stream().collect(Collectors.joining("/"));

    return URI.create(env.getApplicationUrl() + path);
  }
}
