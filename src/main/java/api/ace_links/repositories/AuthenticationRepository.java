package api.ace_links.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import api.ace_links.domain.user.User;

public interface AuthenticationRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);
}
