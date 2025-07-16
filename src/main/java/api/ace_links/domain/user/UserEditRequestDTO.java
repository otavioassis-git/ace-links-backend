package api.ace_links.domain.user;

import java.util.List;

import api.ace_links.domain.link.LinkEditRequestDTO;

public record UserEditRequestDTO(
        String name,
        String surname,
        String avatar,
        String description,
        List<LinkEditRequestDTO> links) {

}
