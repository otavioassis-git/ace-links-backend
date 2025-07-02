package api.ace_links.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDTO(
        @NotBlank(message = "Email is required") String email,
        @NotBlank(message = "Password is required") String password) {

}
