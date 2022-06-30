package com.nermink.phones.exception;

import com.nermink.phones.controller.response.ErrorResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

  @ExceptionHandler(value = ApplicationException.class)
  public ResponseEntity<ErrorResponse> applicationException(ApplicationException exception) {
    var response = buildResponse(exception);
    return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    var appExc = new ApplicationException(ErrorCode.DATA_VALIDATION_VIOLATION,
        HttpStatus.UNPROCESSABLE_ENTITY,
        "Invalid request");

    return new ResponseEntity<>(buildResponse(appExc), appExc.getHttpStatus());
  }

  private ErrorResponse buildResponse(ApplicationException exception) {
    var response = new ErrorResponse();
    response.setCode(exception.getCode().code());
    response.setMessage(exception.getMessage());
    response.setStatus(exception.getHttpStatus().value());
    return response;
  }
}
