package api.ace_links.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterRequestDTO(
                @NotBlank(message = "Email is required") String username,
                @NotBlank(message = "Email is required") String email,
                @NotBlank(message = "Password is required") String password,
                @NotBlank(message = "Name is required") String name,
                String surname,
                String avatar,
                String description,
                @NotNull(message = "Role is required") UserRole role) {
}
