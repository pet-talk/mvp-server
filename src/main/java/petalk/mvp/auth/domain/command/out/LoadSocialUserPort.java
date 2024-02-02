package petalk.mvp.auth.domain.command.out;

import petalk.mvp.auth.domain.command.Authenticator;
import petalk.mvp.auth.domain.command.SocialAuthUser;

import java.util.Optional;

/**
 * 소셜 사용자의 정보를 불러오는 포트입니다.
 */
public interface LoadSocialUserPort {

    Optional<SocialAuthUser> loadSocialUser(Authenticator authenticator);
}
