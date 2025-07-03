package api.ace_links.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import api.ace_links.domain.link.Link;
import api.ace_links.domain.link.LinkUserProjection;

public interface LinkRepository extends JpaRepository<Link, UUID> {

  @Query("SELECT l.id as id, l.title as title, l.url as url, l.icon as icon, l.background as background, l.description as description FROM User u JOIN Link l on u.id = l.user.id WHERE u.id = ?1")
  public List<LinkUserProjection> findLinksByUserId(UUID userId);
}
