package petalk.mvp.http.auth.request;

import petalk.mvp.domain.auth.SocialType;

import java.util.Optional;

/**
 * 소셜 프로필 http 요청자 인터페이스입니다.
 */
public interface SocialProfileRequester {

    Optional<SocialProfile> getProfile(AccessToken accessToken);
    boolean isCorrectType(SocialType type);
}
