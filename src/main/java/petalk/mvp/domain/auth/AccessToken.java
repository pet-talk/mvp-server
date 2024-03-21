package petalk.mvp.domain.auth;

/**
 * 소셜 로그인의 액세스 토큰을 나타내는 클래스입니다.
 */
public class AccessToken {

    private String value;
    private String type;

    private AccessToken(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public static AccessToken of(String token, String tokenType) {
        return new AccessToken(token, tokenType);
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
