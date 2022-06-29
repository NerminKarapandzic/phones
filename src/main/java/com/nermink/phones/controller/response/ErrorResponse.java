package com.nermink.phones.controller.response;

import lombok.Data;

@Data
public class ErrorResponse {

  private Integer code;
  private String message;
  private Integer status;

}
