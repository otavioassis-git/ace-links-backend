package api.ace_links.domain.link;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import api.ace_links.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "links")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Link {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String url;

  @Column(nullable = true)
  private String icon;

  @Column(nullable = true)
  private String background;

  @Column(nullable = true)
  private String description;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Link(String title, String url, String icon, String background, String description, User user) {
    this.title = title;
    this.url = url;
    this.icon = icon;
    this.background = background;
    this.description = description;
    this.user = user;
  }
}
