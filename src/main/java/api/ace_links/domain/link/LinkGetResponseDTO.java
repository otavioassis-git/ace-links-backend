package api.ace_links.domain.link;

import java.util.List;

public record LinkGetResponseDTO(String fullname, String username, String avatar, String description,
    List<LinkUserProjection> links) {
}
