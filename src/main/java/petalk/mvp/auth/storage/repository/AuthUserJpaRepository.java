package petalk.mvp.auth.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petalk.mvp.auth.storage.model.AuthUserJpa;

import java.util.UUID;

public interface AuthUserJpaRepository extends JpaRepository<AuthUserJpa, UUID> {
}
