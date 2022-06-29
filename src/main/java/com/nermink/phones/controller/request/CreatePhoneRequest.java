package com.nermink.phones.controller.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePhoneRequest {
  @NotBlank(message = "field_must_not_be_null_or_empty")
  private String name;
}
