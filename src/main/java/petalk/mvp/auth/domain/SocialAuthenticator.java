package petalk.mvp.auth.domain;

/**
 * 소셜 인증 요청 인터페이스입니다.
 */
public interface SocialAuthenticator {

    AccessToken getAccessToken(String code);
}
