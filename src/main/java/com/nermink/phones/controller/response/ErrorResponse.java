package com.nermink.phones.controller.response;

import java.util.Map;
import lombok.Data;

@Data
public class ErrorResponse {

  private Integer code;
  private String message;
  private Integer status;
  private Map<String, String> errors;

}
