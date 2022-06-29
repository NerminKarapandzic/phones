package com.nermink.phones.controller;

import com.nermink.phones.controller.request.CreatePhoneRequest;
import com.nermink.phones.controller.request.UpdatePhoneRequest;
import com.nermink.phones.controller.response.PhoneResponse;
import com.nermink.phones.service.PhoneService;
import java.util.Collection;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/phones")
public class PhonesController {

  private final PhoneService phoneService;

  @GetMapping
  public ResponseEntity<Collection<PhoneResponse>> getPhones(@RequestParam(required = false) String sortBy) {
    return new ResponseEntity<>(phoneService.getAll(sortBy), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PhoneResponse> createPhone(@Valid @RequestBody CreatePhoneRequest request){
    return new ResponseEntity<>(phoneService.create(request), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PhoneResponse> updatePhone(@PathVariable Integer id,
      @Valid @RequestBody UpdatePhoneRequest request) {
    return new ResponseEntity<>(phoneService.update(id, request), HttpStatus.OK);
  }

}
