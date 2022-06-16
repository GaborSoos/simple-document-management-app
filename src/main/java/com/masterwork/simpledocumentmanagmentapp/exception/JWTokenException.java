package com.masterwork.simpledocumentmanagmentapp.exception;

import org.springframework.security.core.AuthenticationException;

public class JWTokenException extends AuthenticationException {

  public JWTokenException(String message) {
    super(message);
  }

}
