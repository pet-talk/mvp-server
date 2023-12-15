package petalk.mvp.auth.application.command.out;

import petalk.mvp.auth.domain.Authenticator;
import petalk.mvp.auth.domain.SocialAuthUser;

import java.util.Optional;

/**
 * 소셜 사용자의 정보를 불러오는 포트입니다.
 */
public interface LoadSocialUserPort {

    Optional<SocialAuthUser> loadSocialUser(Authenticator authenticator);
}
