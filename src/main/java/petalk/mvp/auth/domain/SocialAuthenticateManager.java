package petalk.mvp.auth.domain;

/**
 * 소셜 인증 매니저 클래스입니다.
 */
public class SocialAuthenticateManager {

    private SocialAuthenticateManager() {
    }
    public static SocialAuthenticateManager getInstance() {
        return new SocialAuthenticateManager();
    }
    public SocialAuthenticator getSocialAuthenticator(SocialType socialType, AccessToken token) {
        switch (socialType) {
            case NAVER -> {
                return NaverAuthenticator.from(token);
            }
            default -> throw new IllegalArgumentException("지원하지 않는 소셜 타입입니다.");
        }
    }
}
