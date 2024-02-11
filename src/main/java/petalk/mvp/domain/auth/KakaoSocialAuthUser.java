package petalk.mvp.domain.auth;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 카카오 유저를 나타내는 클래스입니다.
 *
 */
public class KakaoSocialAuthUser implements SocialAuthUser{
    private SocialAuthId socialAuthId;
    private String email;
    private String nickname;
    private SocialType socialType;

    //== 생성 메소드 ==//

    private KakaoSocialAuthUser(SocialAuthId socialAuthId, String email, String nickname, SocialType socialType) {
        this.socialAuthId = socialAuthId;
        this.email = email;
        this.nickname = nickname;
        this.socialType = socialType;
    }

    public static KakaoSocialAuthUser of(SocialAuthId socialAuthId, String email, String nickname) {
        return new KakaoSocialAuthUser(socialAuthId, email, nickname, SocialType.KAKAO);
    }
    //== 비즈니스 로직 ==//
    @Override
    public AuthUser registerUser(LocalDateTime registrationDate) {
        return AuthUser.register(this.email, this.socialType, this.socialAuthId, this.nickname, this.nickname, registrationDate);
    }
    //== 수정 메소드 ==//
    //== 조회 메소드 ==//

    @Override
    public SocialAuthId getSocialId() {
        return socialAuthId;
    }

    @Override
    public SocialType getSocialType() {
        return socialType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KakaoSocialAuthUser that = (KakaoSocialAuthUser) o;
        return Objects.equals(socialAuthId, that.socialAuthId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socialAuthId);
    }
}
