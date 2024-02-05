package petalk.mvp.domain.auth.command;

/**
 * 네이버 로그인 인증을 요청하는 클래스입니다.
 */
public class Authenticator {
    private AuthorizationCode code;
    private SocialType socialType;

    private Authenticator(AuthorizationCode code, SocialType socialType) {
        this.code = code;
        this.socialType = socialType;
    }

    public static Authenticator of(AuthorizationCode token, SocialType socialType) {
        return new Authenticator(token, socialType);
    }

    public AuthorizationCode getAuthorizationCode() {
        return code;
    }

    public boolean isNaver() {
        return socialType == SocialType.NAVER;
    }

    public boolean isKakao() {
        return socialType == SocialType.KAKAO;
    }

    public boolean isGoogle() {
        return socialType == SocialType.GOOGLE;
    }
}
