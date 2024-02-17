package petalk.mvp.domain.auth;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 구글 유저의 정보를 나타내는 클래스입니다.
 *
 */
public class GoogleSocialAuthUser implements SocialAuthUser{
    private SocialAuthId socialAuthId;
    private String email;
    private String name;
    private String nickname;
    private SocialType socialType;

    //== 생성 메소드 ==//

    private GoogleSocialAuthUser(SocialAuthId socialAuthId, String email, String name, String nickname) {
        this.socialAuthId = socialAuthId;
        this.email = email;
        this.name = name;
        this.socialType = SocialType.GOOGLE;
        this.nickname = nickname;
    }

    public static GoogleSocialAuthUser from(SocialAuthId socialAuthId, String email, String name, String nickname) {
        return new GoogleSocialAuthUser(socialAuthId, email, name, nickname);
    }
    //== 비즈니스 로직 ==//
    @Override
    public AuthUser registerUser(LocalDateTime registrationDate) {
        return AuthUser.register(this.email, this.socialType, this.socialAuthId, this.name, this.nickname, registrationDate);
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
        GoogleSocialAuthUser that = (GoogleSocialAuthUser) o;
        return Objects.equals(socialAuthId, that.socialAuthId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socialAuthId);
    }
}
