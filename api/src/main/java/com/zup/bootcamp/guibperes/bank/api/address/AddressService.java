package com.zup.bootcamp.guibperes.bank.api.address;

import java.util.UUID;

import com.zup.bootcamp.guibperes.bank.base.exceptions.EntityNotFoundedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

  @Autowired
  private AddressRepository addressRepository;

  public void deleteById(UUID id) {
    if (!addressRepository.existsById(id)) {
      throw new EntityNotFoundedException("Cannot find address with provided id");
    }

    addressRepository.deleteById(id);
  }
}
