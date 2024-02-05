package petalk.mvp.domain.auth.command.out;

import petalk.mvp.domain.auth.command.Authenticator;
import petalk.mvp.domain.auth.command.SocialAuthUser;

import java.util.Optional;

/**
 * 소셜 사용자의 정보를 불러오는 포트입니다.
 */
public interface LoadSocialUserPort {

    Optional<SocialAuthUser> loadSocialUser(Authenticator authenticator);
}
