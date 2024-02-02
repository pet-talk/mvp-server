package petalk.mvp.auth.domain.command.out;

import petalk.mvp.auth.domain.command.SocialAuthUser;
import petalk.mvp.auth.domain.command.User;

import java.util.Optional;

/**
 * 소셜 정보와 일치하는 유저를 불러오는 포트입니다.
 */
public interface LoadUserPort {

    Optional<User> loadUser(SocialAuthUser socialAuthUser);
}
