package com.budgetbootstrapper.authorization.service;

import com.budgetbootstrapper.authorization.dto.CustomUserDetails;
import com.budgetbootstrapper.authorization.entity.User;
import com.budgetbootstrapper.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userEntity = userRepository.findUserEntityByUsername(username);
    if (userEntity.isPresent()) {
      return new CustomUserDetails(userEntity.get());
    } else {
      throw new UsernameNotFoundException("User Not Found with -> username: " + username);
    }
  }
}
