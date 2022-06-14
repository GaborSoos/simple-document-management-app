package com.masterwork.simpledocumentmanagmentapp.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {

  private String status;
  private String message;

  public ErrorDto(String message) {
    this.status = "error";
    this.message = message;
  }
}
