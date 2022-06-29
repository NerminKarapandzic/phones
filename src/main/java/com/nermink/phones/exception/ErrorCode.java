package com.nermink.phones.exception;

public enum ErrorCode {

  RESOURCE_NOT_FOUND(40401),
  DATA_CONSTRAINT_VIOLATION(40001),
  UNKNOWN_FIELD(40002);

  private final int code;

  private ErrorCode(int code){
    this.code = code;
  }

  public int code(){
    return code;
  }
}
