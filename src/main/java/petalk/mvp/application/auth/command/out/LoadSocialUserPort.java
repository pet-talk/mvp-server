package petalk.mvp.application.auth.command.out;

import petalk.mvp.domain.auth.AuthorizationCode;
import petalk.mvp.domain.auth.SocialAuthUser;
import petalk.mvp.domain.auth.SocialType;

import java.util.Optional;

/**
 * 소셜 사용자의 정보를 불러오는 포트입니다.
 */
public interface LoadSocialUserPort {

    Optional<SocialAuthUser> loadSocialUser(AuthorizationCode code, SocialType socialType);
}
