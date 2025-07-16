package api.ace_links.domain.link;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record LinkEditRequestDTO(
                UUID id,
                @NotBlank(message = "Rank is required") Integer rank,
                @NotBlank(message = "Title is required") String title,
                @NotBlank(message = "Url is required") String url,
                String icon,
                String background,
                String description,
                Boolean delete) {
}
