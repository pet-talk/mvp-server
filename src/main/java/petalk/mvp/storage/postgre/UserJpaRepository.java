package petalk.mvp.storage.postgre;

import org.springframework.data.jpa.repository.JpaRepository;
import petalk.mvp.storage.postgre.model.UserJpa;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpa, UUID> {
}
