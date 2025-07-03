package api.ace_links.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import api.ace_links.domain.link.LinkGetResponseDTO;
import api.ace_links.domain.link.LinkUpdateRequestDTO;
import api.ace_links.domain.user.User;
import api.ace_links.domain.user.UserExceptionResponseDTO;
import api.ace_links.repositories.UserRepository;
import api.ace_links.services.LinkService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/{userId}")
public class LinkController {

  @Autowired
  private LinkService service;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/links")
  public ResponseEntity<LinkGetResponseDTO> getUserLinks(@PathVariable UUID userId) {
    LinkGetResponseDTO response = new LinkGetResponseDTO(service.getUserLinks(userId));

    return ResponseEntity.ok(response);
  }

  @PostMapping("/links")
  public ResponseEntity<?> updateLinks(@PathVariable UUID userId,
      @RequestBody List<LinkUpdateRequestDTO> links) {
    try {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("User " + userId + " not found"));
      service.updateUserLinks(user, links);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.ok().build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<UserExceptionResponseDTO> handleValidationException(MethodArgumentNotValidException e) {
    var errors = String.join(", ",
        e.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).toList());

    return ResponseEntity.badRequest().body(new UserExceptionResponseDTO(errors));
  }
}
