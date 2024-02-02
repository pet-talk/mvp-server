package petalk.mvp.auth.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petalk.mvp.auth.domain.command.SocialType;
import petalk.mvp.auth.storage.model.AuthUserSocialInfoJpa;

import java.util.Optional;

public interface AuthUserSocialInfoJpaRepository extends JpaRepository<AuthUserSocialInfoJpa, Long> {
    Optional<AuthUserSocialInfoJpa> findOneBySocialIdAndSocialType(String socialId, SocialType socialType);
}
