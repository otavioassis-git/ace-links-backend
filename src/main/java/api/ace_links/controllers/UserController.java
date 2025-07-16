package api.ace_links.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import api.ace_links.domain.user.User;
import api.ace_links.domain.user.UserEditRequestDTO;
import api.ace_links.domain.user.UserExceptionResponseDTO;
import api.ace_links.domain.user.UserUpdatePassowordDTO;
import api.ace_links.services.LinkService;
import api.ace_links.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private LinkService linkService;

  @PutMapping("/{id}")
  public ResponseEntity<?> postMethodName(@PathVariable UUID id, @RequestHeader("Authorization") String token,
      @RequestBody UserEditRequestDTO body) {

    try {
      User user = userService.updateUserInfo(id, token.replace("Bearer ", ""), body);
      linkService.updateUserLinks(user, body.links());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new UserExceptionResponseDTO(e.getMessage()));
    }

    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}/password")
  public ResponseEntity<UserExceptionResponseDTO> updatePassword(@PathVariable UUID id,
      @RequestHeader("Authorization") String token,
      @RequestBody @Valid UserUpdatePassowordDTO body) {
    try {
      userService.updatePassword(id, token.replace("Bearer ", ""), body.newPassword());
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new UserExceptionResponseDTO(e.getMessage()));
    }
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<UserExceptionResponseDTO> handleValidationException(MethodArgumentNotValidException e) {
    var errors = String.join(", ",
        e.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).toList());

    return ResponseEntity.badRequest().body(new UserExceptionResponseDTO(errors));
  }
}
