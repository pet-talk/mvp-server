package petalk.mvp.domain.auth;

import java.time.LocalDateTime;

/**
 * 써드 파티 유저 인터페이스입니다.
 *
 */
public interface SocialAuthUser {

    SocialAuthId getSocialId();

    SocialType getSocialType();

    User registerUser(LocalDateTime registrationDate);

    UserSocialInfo registerInfo(User user);
}
