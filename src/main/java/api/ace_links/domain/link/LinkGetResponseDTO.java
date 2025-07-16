package api.ace_links.domain.link;

import java.util.List;
import java.util.UUID;

public record LinkGetResponseDTO(UUID id, String name, String username, String avatar, String description,
        List<LinkUserProjection> links) {
}
