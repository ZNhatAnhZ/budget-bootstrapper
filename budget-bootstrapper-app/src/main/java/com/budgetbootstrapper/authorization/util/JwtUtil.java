package com.budgetbootstrapper.authorization.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.budgetbootstrapper.authorization.dto.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

  @Value("${jwtSecret:secret}")
  private String jwtSecret;

  @Value("${jwtExpiration:1000}")
  private int jwtExpiration;

  public String generateJwtToken(CustomUserDetails customUserDetails) {
    return JWT.create()
        .withClaim("id", customUserDetails.getUser().getId())
        .withClaim("username", customUserDetails.getUsername())
        // .withClaim("authorities",
        // customUserDetails.getAuthorities().stream().toList())
        .withIssuedAt(new Date())
        .withExpiresAt(new Date((new Date()).getTime() + jwtExpiration))
        .sign(Algorithm.HMAC256(jwtSecret));
  }

  public String getUserNameFromJwtToken(String token) {
    return JWT.decode(token).getClaims().get("username").asString();
  }

  public Collection<? extends GrantedAuthority> getAuthoritiesFromJwtToken(String token) {
    return JWT.decode(token).getClaims().get("authorities").asList(GrantedAuthority.class);
  }

  public boolean validateJwtToken(String authToken) {
    if (Boolean.FALSE.equals(isTokenExpired(authToken))) {
      try {
        JWT.require(Algorithm.HMAC256(jwtSecret)).build().verify(authToken);
        return true;
      } catch (JWTVerificationException e) {
        log.error("JWT verification failed: {}", e.getMessage(), e);
        return false;
      }
    }

    return false;
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = JWT.decode(token).getExpiresAt();
    return expiration.before(new Date());
  }
}
