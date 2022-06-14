package com.masterwork.simpledocumentmanagmentapp.security.filter;

import com.masterwork.simpledocumentmanagmentapp.security.model.entity.UserDetailsImpl;
import com.masterwork.simpledocumentmanagmentapp.security.service.UserServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.masterwork.simpledocumentmanagmentapp.security.configuration.SecurityConfig.PRIVATE_ENDPOINTS;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private UserServiceImpl userServiceImpl;

  public JwtAuthenticationFilter(UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getJWTfromRequest(request);
    SecretKey key = Keys.hmacShaKeyFor(
        getJWT_KEY().getBytes(StandardCharsets.UTF_8));
    UserDetailsImpl userDetails = convert(token, key);
    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(auth);

    filterChain.doFilter(request, response);
  }

  @Override
  public Environment getEnvironment() {
    return super.getEnvironment();
  }

  private String getJWTfromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    } else {
      //TODO: cserélni
      throw new RuntimeException("Invalid JWT token.");
    }
  }

  public UserDetailsImpl convert(String token, SecretKey key) {
    try{
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token)
          .getBody();
      String userName = String.valueOf(claims.get("username"));
      return (UserDetailsImpl) userServiceImpl.loadUserByUsername(userName);
          //TODO: cserélni az exceptionokat
    } catch (SignatureException e) {
      throw new RuntimeException("Invalid JWT signature");
    } catch (MalformedJwtException e) {
      throw new RuntimeException("Invalid JWT token");
    } catch (ExpiredJwtException e) {
      throw new RuntimeException("Expired JWT token");
    } catch (UnsupportedJwtException e) {
      throw new RuntimeException("Unsupported JWT token");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("JWT claims string is empty");
    }
  }

  private String getJWT_KEY() {
    return getEnvironment().getProperty("JWT_KEY");
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();
    List<String> privateEndpoints = Arrays.stream(PRIVATE_ENDPOINTS)
        .map(endpoint -> endpoint.replaceAll("/\\*\\*", ""))
        .collect(Collectors.toList());
    return !privateEndpoints.stream().anyMatch(e -> path.startsWith(e));
  }
}
