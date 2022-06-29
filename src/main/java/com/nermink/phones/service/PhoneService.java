package com.nermink.phones.service;

import com.nermink.phones.controller.request.CreatePhoneRequest;
import com.nermink.phones.controller.request.UpdatePhoneRequest;
import com.nermink.phones.controller.response.PhoneResponse;
import com.nermink.phones.domain.model.Phone;
import com.nermink.phones.domain.repository.PhoneRepository;
import com.nermink.phones.exception.ApplicationException;
import com.nermink.phones.exception.ErrorCode;
import java.util.Collection;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneService {

  private final PhoneRepository phoneRepository;

  public Collection<PhoneResponse> getAll(String sortBy) {
    if (sortBy != null) {

      if (!sortBy.equals("name")) {
        throw new ApplicationException(
            ErrorCode.UNKNOWN_FIELD,
            HttpStatus.UNPROCESSABLE_ENTITY,
            String.format("%s is not a known field on %s", sortBy, Phone.class.getName()));
      }

      return phoneRepository.findAll(Comparator.comparing(Phone::getName)).stream()
          .map(PhoneResponse::new).toList();
    }

    return phoneRepository.findAll().stream().map(PhoneResponse::new).toList();
  }

  public PhoneResponse create(CreatePhoneRequest request) {
    var phone = new Phone(request.getName());

    return new PhoneResponse(phoneRepository.save(phone));
  }

  public PhoneResponse update(Integer id, UpdatePhoneRequest request) {
    var phone = phoneRepository.findById(id).orElseThrow(
        () -> new ApplicationException(
            ErrorCode.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND,
            String.format("Resource with id: %s was not found", id)));

    //TODO:If we set the name and the unique validation fails name will still be changed
    phone.setName(request.getName());

    return new PhoneResponse(phoneRepository.save(phone));
  }
}
