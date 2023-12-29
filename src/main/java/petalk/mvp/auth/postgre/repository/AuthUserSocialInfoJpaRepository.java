package petalk.mvp.auth.postgre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petalk.mvp.auth.domain.SocialType;
import petalk.mvp.auth.postgre.model.AuthUserSocialInfoJpa;

import java.util.Optional;

public interface AuthUserSocialInfoJpaRepository extends JpaRepository<AuthUserSocialInfoJpa, Long> {
    Optional<AuthUserSocialInfoJpa> findOneBySocialIdAndSocialType(String socialId, SocialType socialType);
}
