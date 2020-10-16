package com.zup.bootcamp.guibperes.bank.api.image;

import java.util.UUID;

import com.zup.bootcamp.guibperes.bank.base.annotations.TransactionalService;
import com.zup.bootcamp.guibperes.bank.base.exceptions.EntityNotFoundedException;
import com.zup.bootcamp.guibperes.bank.base.storages.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@TransactionalService
public class ImageService {

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private StorageService storageService;

  private Image findImageById(UUID id) {
    return imageRepository
      .findById(id)
      .orElseThrow(() -> new EntityNotFoundedException("Cannot find image with provided id"));
  }

  public Image save(MultipartFile file, UUID proposalId) {
    var fileName = storageService.store(file, proposalId.toString());
    var image = Image.of(fileName, file.getContentType(), file.getSize());

    var savedImage = imageRepository.save(image);
    return savedImage;
  }

  public void deleteById(UUID id) {
    var image = findImageById(id);

    storageService.deleteByName(image.getName());
    imageRepository.deleteById(id);
  }
}
