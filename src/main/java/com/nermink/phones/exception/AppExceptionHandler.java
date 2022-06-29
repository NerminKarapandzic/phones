package com.nermink.phones.exception;

import com.nermink.phones.controller.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

  @ExceptionHandler(value = ApplicationException.class)
  public ResponseEntity<ErrorResponse> applicationException(ApplicationException exception){
    var response = buildResponse(exception);
    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
  }

  private ErrorResponse buildResponse(ApplicationException exception){
    var response = new ErrorResponse();
    response.setCode(exception.getCode().code());
    response.setMessage(exception.getMessage());
    response.setStatus(exception.getHttpStatus().value());
    return response;
  }
}
