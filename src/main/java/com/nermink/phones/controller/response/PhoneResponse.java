package com.nermink.phones.controller.response;

import com.nermink.phones.domain.model.Phone;
import lombok.Getter;

@Getter
public class PhoneResponse {
  private Integer id;
  private String name;

  public PhoneResponse(Phone phone) {
    this.id = phone.getId();
    this.name = phone.getName();
  }
}
