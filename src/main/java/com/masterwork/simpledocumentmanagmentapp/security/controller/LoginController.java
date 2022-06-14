package com.masterwork.simpledocumentmanagmentapp.security.controller;

import com.masterwork.simpledocumentmanagmentapp.security.model.dto.LoginReqDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.LoginResDto;
import com.masterwork.simpledocumentmanagmentapp.security.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResDto> authenticateUser(@RequestBody LoginReqDto loginReqDto) {
    return ResponseEntity.status(200).body(loginService.authenticateUser(loginReqDto));
  }

}
