package petalk.mvp.http.auth.adapter;

import petalk.mvp.domain.auth.AccessToken;
import petalk.mvp.domain.auth.SocialType;

import java.util.Optional;

/**
 * 소셜 프로필을 불러오는 인터페이스입니다.
 */
public interface SocialProfileReader {

    Optional<SocialProfile> getProfile(AccessToken token);
    boolean isCorrectType(SocialType type);
}
