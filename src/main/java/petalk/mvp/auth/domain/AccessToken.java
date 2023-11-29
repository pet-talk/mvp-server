package petalk.mvp.auth.domain;

/**
 * 소셜 로그인의 액세스 토큰을 나타내는 클래스입니다.
 */
public class AccessToken {

    private String token;

    private AccessToken(String token) {
        this.token = token;
    }

    public static AccessToken from(String token) {
        return new AccessToken(token);
    }

    public String getValue() {
        return token;
    }
}
