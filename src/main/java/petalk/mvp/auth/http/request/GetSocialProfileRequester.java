package petalk.mvp.auth.http.request;

import petalk.mvp.auth.http.model.AccessToken;
import petalk.mvp.auth.http.model.SocialProfile;

import java.util.Optional;

/**
 * 소셜 프로필 http 요청자 인터페이스입니다.
 */
public interface GetSocialProfileRequester {

    Optional<SocialProfile> getProfile(AccessToken accessToken);
}
