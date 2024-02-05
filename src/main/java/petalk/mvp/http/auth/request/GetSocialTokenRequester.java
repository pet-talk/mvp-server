package petalk.mvp.http.auth.request;

import petalk.mvp.domain.auth.command.Authenticator;
import petalk.mvp.http.auth.request.AccessToken;

import java.util.Optional;

/**
 * 소셜 엑세스 토큰 http 요청자 인터페이스입니다.
 */
public interface GetSocialTokenRequester {

    Optional<AccessToken> getAccessToken(Authenticator authenticator);
}
