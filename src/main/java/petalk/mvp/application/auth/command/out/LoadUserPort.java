package petalk.mvp.application.auth.command.out;

import petalk.mvp.domain.auth.AuthUser;
import petalk.mvp.domain.auth.SocialAuthUser;

import java.util.Optional;

/**
 * 소셜 정보와 일치하는 유저를 불러오는 포트입니다.
 */
public interface LoadUserPort {

    Optional<AuthUser> loadUser(SocialAuthUser socialAuthUser);
}
