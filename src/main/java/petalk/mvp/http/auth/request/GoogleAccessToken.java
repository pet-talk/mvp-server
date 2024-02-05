package petalk.mvp.http.auth.request;

/**
 * third party 서비스의 액세스 토큰을 나타냅니다.
 */
public class GoogleAccessToken implements AccessToken {
    private String accessToken;
    private String tokenType;
    private String scope;
    private int expiresIn;

    public GoogleAccessToken(String accessToken, String tokenType, String scope, String expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.scope = scope;
        if (expiresIn != null)
            this.expiresIn = Integer.parseInt(expiresIn);
        if (expiresIn == null)
            this.expiresIn = 0;
        this.validate();
    }

    public void validate() {
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalArgumentException("accessToken is null or empty");
        }
        if (tokenType == null || tokenType.isBlank()) {
            throw new IllegalArgumentException("tokenType is null or empty");
        }
        if (scope == null || scope.isBlank()) {
            throw new IllegalArgumentException("scope is null or empty");
        }
        if (expiresIn <= 0) {
            throw new IllegalArgumentException("expiresIn is less than or equal to zero");
        }
    }

    @Override
    public String generateAuthenticationCode() {
        return accessToken;
    }
}