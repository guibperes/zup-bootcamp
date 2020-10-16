package com.zup.bootcamp.guibperes.bank.base.storages;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  public String store(MultipartFile file, String fileName);

  public Resource loadByName(String fileName);

  public void deleteByName(String fileName);
}
