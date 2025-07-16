package api.ace_links.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.ace_links.domain.link.Link;
import api.ace_links.domain.link.LinkEditRequestDTO;
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

  public void updateUserLinks(User user, List<LinkEditRequestDTO> links) throws IllegalArgumentException {
    for (LinkEditRequestDTO link : links) {
      if (link.id() == null) {
        Link newLink = new Link(link.rank(), link.title(), link.url(), link.icon(), link.background(),
            link.description(), user);
        linkRepository.save(newLink);
      } else if (link.delete()) {
        linkRepository.delete(linkRepository.findById(link.id())
            .orElseThrow(() -> new IllegalArgumentException("Link " + link.id() + " not found")));
      } else {
        Link linkToUpdate = linkRepository.findById(link.id())
            .orElseThrow(() -> new IllegalArgumentException("Link " + link.id() + " not found"));
        if (link.rank() != null)
          linkToUpdate.setRank(link.rank());
        if (link.title() != null)
          linkToUpdate.setTitle(link.title());
        if (link.url() != null)
          linkToUpdate.setUrl(link.url());
        if (link.icon() != null)
          linkToUpdate.setIcon(link.icon());
        if (link.background() != null)
          linkToUpdate.setBackground(link.background());
        if (link.description() != null)
          linkToUpdate.setDescription(link.description());
        linkRepository.save(linkToUpdate);
      }
    }
  }
}
