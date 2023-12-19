package petalk.mvp.auth.http.request;

import petalk.mvp.auth.domain.Authenticator;
import petalk.mvp.auth.http.model.AccessToken;

import java.util.Optional;

/**
 * 소셜 엑세스 토큰 http 요청자 인터페이스입니다.
 */
public interface GetSocialTokenRequester {

    Optional<AccessToken> getAccessToken(Authenticator authenticator);
}
