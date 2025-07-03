package api.ace_links.domain.link;

import java.util.List;

public record LinkGetResponseDTO(List<LinkUserProjection> links) {
}
