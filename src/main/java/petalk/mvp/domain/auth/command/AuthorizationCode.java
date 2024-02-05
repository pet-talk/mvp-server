package petalk.mvp.domain.auth.command;

/**
 * 소셜 로그인의 액세스 토큰을 나타내는 클래스입니다.
 */
public class AuthorizationCode {

    private String code;

    private AuthorizationCode(String code) {
        this.code = code;
    }

    public static AuthorizationCode from(String token) {
        return new AuthorizationCode(token);
    }

    public String getValue() {
        return code;
    }
}
