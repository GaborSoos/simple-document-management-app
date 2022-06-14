package com.masterwork.simpledocumentmanagmentapp.exception;

import com.masterwork.simpledocumentmanagmentapp.exception.model.ErrorDto;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ExceptionService {

  ErrorDto createInputParamsValidErrorMessage(MethodArgumentNotValidException e);
}
