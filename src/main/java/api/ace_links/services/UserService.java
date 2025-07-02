package api.ace_links.services;

import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api.ace_links.domain.user.User;
import api.ace_links.domain.user.UserRole;
import api.ace_links.infra.security.TokenService;
import api.ace_links.repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenService tokenService;

  @Autowired
  PasswordEncoder passwordEncoder;

  public void updatePassword(UUID id, String token, String newPassword) throws BadRequestException {
    User userRequested = userRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Requested user not found"));

    var tokenEmail = tokenService.validateToken(token);
    User userRequester = userRepository.findByEmail(tokenEmail)
        .orElseThrow(() -> new BadRequestException("Requester user not found"));

    if (userRequester.getRole() != UserRole.ADMIN && userRequester.getEmail() != userRequested.getEmail())
      throw new BadRequestException("Can't update other user password");

    userRequested.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(userRequested);
  }
}
