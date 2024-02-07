package petalk.mvp.storage.postgre;

import org.springframework.data.jpa.repository.JpaRepository;
import petalk.mvp.domain.auth.SocialType;
import petalk.mvp.storage.postgre.model.AuthUserSocialInfoJpa;

import java.util.Optional;

public interface AuthUserSocialInfoJpaRepository extends JpaRepository<AuthUserSocialInfoJpa, Long> {
    Optional<AuthUserSocialInfoJpa> findOneBySocialIdAndSocialType(String socialId, SocialType socialType);
}
