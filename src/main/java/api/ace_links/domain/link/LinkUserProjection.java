package api.ace_links.domain.link;

import java.util.UUID;

public interface LinkUserProjection {
  UUID getId();

  String getTitle();

  String getUrl();

  String getIcon();

  String getBackground();

  String getDescription();

  Integer getRank();
}
