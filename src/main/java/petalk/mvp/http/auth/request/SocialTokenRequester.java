package petalk.mvp.http.auth.request;

import petalk.mvp.domain.auth.AuthorizationCode;
import petalk.mvp.domain.auth.SocialType;

import java.util.Optional;

/**
 * 소셜 엑세스 토큰 http 요청자 인터페이스입니다.
 */
public interface SocialTokenRequester {

    Optional<AccessToken> getAccessToken(AuthorizationCode code);

    boolean isCorrectType(SocialType type);
}
