package com.masterwork.simpledocumentmanagmentapp.security.service;

import com.masterwork.simpledocumentmanagmentapp.security.model.dto.LoginReqDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.LoginResDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {

  private final UserServiceImpl userServiceImpl;
  private final PasswordEncoder passwordEncoder;
  //TODO
  @Value("${security.jwt-key:}")
  private String jwtKey;

  public LoginServiceImpl(UserServiceImpl userServiceImpl, PasswordEncoder passwordEncoder) {
    this.userServiceImpl = userServiceImpl;
    this.passwordEncoder = passwordEncoder;
  }

  //Todo: create new exceptions: e.g. RequestedResourceForbidenException
  @Override
  public LoginResDto authenticateUser(LoginReqDto loginReqDto) {
    UserDetailsImpl userDetails = (UserDetailsImpl) userServiceImpl.loadUserByUsername(loginReqDto.getUserName());
    if(!passwordEncoder.matches(loginReqDto.getPassword(), userDetails.getPassword())) {
      throw new RuntimeException("Password is incorrect.");
    }
    if(!userDetails.isEnabled()) {
      throw new RuntimeException("Requested user is not enabled.");
    }
    return new LoginResDto("successful authentication", generateJwtString(userDetails));
  }

  @Override
  public String generateJwtString(UserDetailsImpl userDetails) {
    SecretKey key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
    return Jwts.builder()
        .setSubject("DocManApp")
        .claim("username", userDetails.getUsername())
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plus(30 * 60, ChronoUnit.SECONDS)))
        .signWith(key)
        .compact();
  }
}
