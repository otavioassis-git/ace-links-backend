package api.ace_links.domain.user;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserLoginResponseDTO(UUID id, String name, String username, String email, UserRole role, String token,
    String error) {
}
