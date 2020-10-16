package com.zup.bootcamp.guibperes.bank.api.image;

import java.util.UUID;

import com.zup.bootcamp.guibperes.bank.base.annotations.RestConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@RestConfig
@Api(tags = "Image")
@RequestMapping("/images")
public class ImageController {

  @Autowired
  private ImageService imageService;

  @GetMapping("/{id}")
  public ResponseEntity<Resource> findById(@PathVariable UUID id) {
    var image = imageService.findById(id);

    return ResponseEntity.ok()
      .contentType(MediaType.valueOf(image.getType()))
      .body(image.getResource());
  }
}
