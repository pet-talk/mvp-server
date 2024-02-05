package petalk.mvp.storage.postgre;

import org.springframework.data.jpa.repository.JpaRepository;
import petalk.mvp.storage.postgre.model.AuthUserJpa;

import java.util.UUID;

public interface AuthUserJpaRepository extends JpaRepository<AuthUserJpa, UUID> {
}
