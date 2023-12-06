package petalk.mvp.auth.domain;

/**
 * 네이버 로그인 인증을 요청하는 클래스입니다.
 */
public class NaverAuthenticator implements SocialAuthenticator {
    private AccessToken token;

    private NaverAuthenticator(AccessToken token) {
        this.token = token;
    }

    public static NaverAuthenticator from(AccessToken token) {
        return new NaverAuthenticator(token);
    }

    @Override
    public AccessToken getAccessToken(String code) {
        return token;
    }
}
