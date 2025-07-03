package api.ace_links.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.ace_links.domain.link.Link;
import api.ace_links.domain.link.LinkUpdateRequestDTO;
import api.ace_links.domain.link.LinkUserProjection;
import api.ace_links.domain.user.User;
import api.ace_links.repositories.LinkRepository;
import api.ace_links.repositories.UserRepository;

@Service
public class LinkService {

  @Autowired
  private LinkRepository linkRepository;

  @Autowired
  private UserRepository userRepository;

  public List<LinkUserProjection> getUserLinks(UUID userId) {
    List<LinkUserProjection> links = linkRepository.findLinksByUserId(userId);

    return links;
  }

  public void updateUserLinks(UUID userId, List<LinkUpdateRequestDTO> links) throws IllegalArgumentException {
    for (LinkUpdateRequestDTO link : links) {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("User not found"));

      if (link.id() == null) {
        Link newLink = new Link(link.title(), link.url(), link.icon(), link.background(), link.description(), user);
        linkRepository.save(newLink);
      } else if (link.delete()) {
        linkRepository.delete(linkRepository.findById(link.id()).orElseThrow());
      } else {
        Link linkToUpdate = linkRepository.findById(link.id()).orElseThrow();
        linkToUpdate.setTitle(link.title());
        linkToUpdate.setUrl(link.url());
        linkToUpdate.setIcon(link.icon());
        linkToUpdate.setBackground(link.background());
        linkToUpdate.setDescription(link.description());
        linkRepository.save(linkToUpdate);
      }
    }
  }
}
