package petalk.mvp.auth.application.command.out;

import petalk.mvp.auth.domain.SocialAuthUser;
import petalk.mvp.auth.domain.User;

/**
 * 이 인터페이스는 사용자의 소셜 정보를 등록하기 위한 포트를 나타냅니다.
 */
public interface RegisterSocialInfoPort {

    void registerSocialInfo(User user, SocialAuthUser socialAuthUser);
}
