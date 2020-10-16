package com.zup.bootcamp.guibperes.bank.base.storages.implementations;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.zup.bootcamp.guibperes.bank.base.exceptions.InternalServerError;
import com.zup.bootcamp.guibperes.bank.base.storages.StorageService;
import com.zup.bootcamp.guibperes.bank.configs.EnvironmentValues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("FILE_SYSTEM")
public class FilesystemStorageService implements StorageService {

  @Autowired
  private EnvironmentValues env;

  @Override
  public String store(MultipartFile file, String fileName) {
    String fileExtension = file.getOriginalFilename().split("\\.")[1].toLowerCase();

    Path filePath = Path.of(env.getImagesFolder(), fileName + "." + fileExtension);

    try (InputStream fileStream = file.getInputStream()) {
      Files.copy(fileStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      e.printStackTrace();
      throw new InternalServerError("Cannot save file on filesytem");
    }

    return filePath.getFileName().toString();
  }

  @Override
  public Resource loadByName(String fileName) {
    Path filePath = Path.of(env.getImagesFolder(), fileName);

    try {
      Resource resource = new UrlResource(filePath.toUri());

      if (!resource.exists() || !resource.isReadable()) {
        throw new InternalServerError("Cannot load file of filesytem");
      }

      return resource;
    } catch (Exception e) {
      e.printStackTrace();
      throw new InternalServerError("Cannot load file of filesytem");
    }
  }

  @Override
  public void deleteByName(String fileName) {
    Path filePath = Path.of(env.getImagesFolder(), fileName);

    try {
      Files.deleteIfExists(filePath);
    } catch (Exception e) {
      e.printStackTrace();
      throw new InternalServerError("Cannot delete file of filesytem");
    }
  }
}
