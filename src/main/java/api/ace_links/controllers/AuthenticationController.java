package api.ace_links.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import api.ace_links.domain.user.User;
import api.ace_links.domain.user.UserLoginRequestDTO;
import api.ace_links.domain.user.UserLoginResponseDTO;
import api.ace_links.domain.user.UserRegisterRequestDTO;
import api.ace_links.domain.user.UserRole;
import api.ace_links.domain.user.UserExceptionResponseDTO;
import api.ace_links.infra.security.TokenService;
import api.ace_links.repositories.AuthenticationRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserLoginResponseDTO> create(@RequestBody @Valid UserRegisterRequestDTO body) {
        if (body.role() == UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new UserLoginResponseDTO(null, null, null, null, null, "Admin role is not allowed"));
        }

        if (repository.findByEmail(body.email()) != null)
            return ResponseEntity.badRequest()
                    .body(new UserLoginResponseDTO(null, null, null, null, null, "Email already registered"));

        User newUser = new User(body.username(), body.email(), passwordEncoder.encode(body.password()), body.name(),
                body.surname(),
                body.avatar(),
                body.description(), body.role());
        String token = tokenService.generateToken(newUser);

        System.out.println(body.description());
        System.out.println(newUser.getDescription());

        repository.save(newUser);
        return ResponseEntity.ok(new UserLoginResponseDTO(newUser.getId(), newUser.getName(), newUser.getEmail(),
                newUser.getRole(), token, null));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody @Valid UserLoginRequestDTO body) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            User user = (User) auth.getPrincipal();
            var token = tokenService.generateToken(user);

            return ResponseEntity.ok(new UserLoginResponseDTO(user.getId(), user.getName(), user.getEmail(),
                    user.getRole(), token, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new UserLoginResponseDTO(null, null, null, null, null, e.getMessage()));
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
