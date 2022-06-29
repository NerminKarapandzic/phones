package com.nermink.phones.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException{

  private ErrorCode code;
  private HttpStatus httpStatus;

  public ApplicationException(ErrorCode code, HttpStatus status, String message) {
    super(message);
    this.code = code;
    this.httpStatus = status;
  }
}
