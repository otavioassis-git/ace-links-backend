package api.ace_links.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.ace_links.domain.link.Link;
import api.ace_links.domain.link.LinkUpdateRequestDTO;
import api.ace_links.domain.link.LinkUserProjection;
import api.ace_links.domain.user.User;
import api.ace_links.repositories.LinkRepository;

@Service
public class LinkService {

  @Autowired
  private LinkRepository linkRepository;

  public List<LinkUserProjection> getUserLinks(User user) {
    List<LinkUserProjection> links = linkRepository.findLinksByUserId(user.getId());

    return links;
  }

  public void updateUserLinks(User user, List<LinkUpdateRequestDTO> links) throws IllegalArgumentException {
    for (LinkUpdateRequestDTO link : links) {
      if (link.id() == null) {
        Link newLink = new Link(link.title(), link.url(), link.icon(), link.background(), link.description(), user);
        linkRepository.save(newLink);
      } else if (link.delete()) {
        linkRepository.delete(linkRepository.findById(link.id())
            .orElseThrow(() -> new IllegalArgumentException("Link " + link.id() + " not found")));
      } else {
        Link linkToUpdate = linkRepository.findById(link.id())
            .orElseThrow(() -> new IllegalArgumentException("Link " + link.id() + " not found"));
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
