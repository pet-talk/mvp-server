package petalk.mvp.auth.http.model;

import petalk.mvp.auth.domain.command.SocialAuthUser;

/**
 * 소셜 프로필을 추상화합니다.<br>
 * 추상화된 소셜 프로필은 소셜 인증 유저로 변환하는 역할을 수행합니다.
 */
public interface SocialProfile {

    SocialAuthUser toSocialAuthUser();
}
