package api.ace_links.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserUpdatePassowordDTO(@NotBlank(message = "New password is required") String newPassword) {

}
