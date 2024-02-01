package petalk.mvp.auth.http.model;

/**
 * third party 서비스의 액세스 토큰을 나타냅니다.
 */
public class NaverAccessToken implements AccessToken {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private int expiresIn;

    public NaverAccessToken(String accessToken, String tokenType, String refreshToken, String expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
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
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("refreshToken is null or empty");
        }
        if (expiresIn <= 0) {
            throw new IllegalArgumentException("expiresIn is less than or equal to zero");
        }
    }

    @Override
    public String generateAuthenticationCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(tokenType);
        sb.append(" ");
        sb.append(accessToken);
        return sb.toString();
    }
}