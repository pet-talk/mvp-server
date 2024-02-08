package petalk.mvp.http.auth.adapter;

import petalk.mvp.domain.auth.SocialType;

import java.util.Optional;

/**
 * 소셜 프로필을 불러오는 인터페이스입니다.
 */
public interface SocialProfileReader {

    Optional<SocialProfile> getProfile(SocialTokenResponse tokenResponse);
    boolean isCorrectType(SocialType type);
}
