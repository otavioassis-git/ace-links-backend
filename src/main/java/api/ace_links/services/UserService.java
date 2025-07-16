package api.ace_links.services;

import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api.ace_links.domain.user.User;
import api.ace_links.domain.user.UserEditRequestDTO;
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

  public User updateUserInfo(UUID id, String token, UserEditRequestDTO body) throws BadRequestException {
    User userRequested = userRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Requested user not found"));

    var tokenEmail = tokenService.validateToken(token);
    User userRequester = userRepository.findByEmail(tokenEmail)
        .orElseThrow(() -> new BadRequestException("Requester user not found"));

    if (userRequester.getRole() != UserRole.ADMIN && userRequester.getEmail() != userRequested.getEmail())
      throw new BadRequestException("Can't update other users info");

    if (body.name() != null)
      userRequested.setName(body.name());
    if (body.surname() != null)
      userRequested.setSurname(body.surname());
    if (body.avatar() != null)
      userRequested.setAvatar(body.avatar());
    if (body.description() != null)
      userRequested.setDescription(body.description());
    userRepository.save(userRequested);

    return userRequested;
  }

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
