package com.masterwork.simpledocumentmanagmentapp.exception;

import com.masterwork.simpledocumentmanagmentapp.exception.model.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
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

  @ExceptionHandler(RequestCauseConflictException.class)
  public ResponseEntity<ErrorDto> handleResourceNotFoundException(RequestCauseConflictException e) {
    return ResponseEntity.status(409).body(new ErrorDto(e.getMessage()));
  }

  @ExceptionHandler({ForbiddenActionException.class, AccessDeniedException.class})
  public ResponseEntity<ErrorDto> handleForbiddenAction(RuntimeException e) {
    return ResponseEntity.status(403).body(new ErrorDto(e.getMessage()));
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    return ResponseEntity.status(400).body(exceptionService.createInputParamsValidErrorMessage(e));
  }

}
