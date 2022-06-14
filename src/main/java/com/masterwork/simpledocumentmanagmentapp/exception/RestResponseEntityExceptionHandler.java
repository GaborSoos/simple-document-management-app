package com.masterwork.simpledocumentmanagmentapp.exception;

import com.masterwork.simpledocumentmanagmentapp.exception.model.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private final ExceptionService exceptionService;

  public RestResponseEntityExceptionHandler(ExceptionService exceptionService) {
    this.exceptionService = exceptionService;
  }

  @ExceptionHandler(RequestedResourceNotFoundException.class)
  public ResponseEntity<ErrorDto> handleResourceNotFoundException(RequestedResourceNotFoundException e) {
    return ResponseEntity.status(404).body(new ErrorDto(e.getMessage()));
  }

  // TODO: why object
  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    return ResponseEntity.status(400).body(exceptionService.createInputParamsValidErrorMessage(e));
  }

}
