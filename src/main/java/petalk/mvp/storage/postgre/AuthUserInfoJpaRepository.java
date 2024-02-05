package petalk.mvp.storage.postgre;

import org.springframework.data.jpa.repository.JpaRepository;
import petalk.mvp.storage.postgre.model.AuthUserInfoJpa;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserInfoJpaRepository extends JpaRepository<AuthUserInfoJpa, Long> {
    Optional<AuthUserInfoJpa> findOneByUserId(UUID userId);
}
