package petalk.mvp.auth.postgre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petalk.mvp.auth.postgre.model.AuthUserJpa;

import java.util.UUID;

public interface AuthUserJpaRepository extends JpaRepository<AuthUserJpa, UUID> {
}
