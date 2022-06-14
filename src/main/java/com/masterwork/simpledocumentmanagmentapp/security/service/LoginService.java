package com.masterwork.simpledocumentmanagmentapp.security.service;

import com.masterwork.simpledocumentmanagmentapp.security.model.dto.LoginReqDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.LoginResDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.UserDetailsImpl;

public interface LoginService {

  LoginResDto authenticateUser(LoginReqDto loginReqDto);

  String generateJwtString(UserDetailsImpl userDetails);
}
