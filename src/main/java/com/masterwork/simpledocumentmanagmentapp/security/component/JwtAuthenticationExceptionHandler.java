package com.masterwork.simpledocumentmanagmentapp.security.component;

import com.google.gson.Gson;
import com.masterwork.simpledocumentmanagmentapp.exception.model.ErrorDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationExceptionHandler implements AuthenticationEntryPoint {
  //TODO: what contain authException.getMessage????
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    String token = request.getHeader("Authorization");
    String errorMessage = token == null ? "No authentication token is provided!" : "Authentication token is invalid!";
    ErrorDto error = new ErrorDto(errorMessage);
    response.setStatus(401);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    String errorJson = new Gson().toJson(error);
    PrintWriter out = response.getWriter();
    out.println(errorJson);
    out.flush();
  }
}
