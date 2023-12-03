package petalk.mvp.auth.domain;

/**
 * 네이버 로그인 인증을 요청하는 클래스입니다.
 */
public class NaverAuthenticator implements SocialAuthenticator {
    private AccessToken token;

    @Override
    public AccessToken getAccessToken(String code) {
        return token;
    }
}
