package com.budgetbootstrapper.authorization.controller;

import com.budgetbootstrapper.authorization.dto.CustomUserDetails;
import com.budgetbootstrapper.authorization.dto.UserInfo;
import com.budgetbootstrapper.authorization.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/login")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final JwtUtil jwtUtil;


  @PostMapping
  public ResponseEntity<?> login(@RequestBody UserInfo userInfo) {
    Map<String, Object> responseMap = new HashMap<>();
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      userInfo.getUsername(), userInfo.getPassword()));

      if (authentication.isAuthenticated()) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        userInfo.setJwtToken(jwtUtil.generateJwtToken(customUserDetails));
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
      }

    } catch (BadCredentialsException e) {
      responseMap.put("message", "Invalid Credentials");
      return ResponseEntity.status(401).body(responseMap);
    }
    return null;
  }
}
