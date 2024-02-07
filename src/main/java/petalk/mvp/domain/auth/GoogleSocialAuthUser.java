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
    private SocialType socialType;

    //== 생성 메소드 ==//

    private GoogleSocialAuthUser(SocialAuthId socialAuthId, String email, String name) {
        this.socialAuthId = socialAuthId;
        this.email = email;
        this.name = name;
        this.socialType = SocialType.GOOGLE;
    }

    public static GoogleSocialAuthUser from(SocialAuthId socialAuthId, String email, String name) {
        return new GoogleSocialAuthUser(socialAuthId, email, name);
    }
    //== 비즈니스 로직 ==//
    @Override
    public UserSocialInfo registerInfo(User user) {
        return UserSocialInfo.register(user.getId(), this.email, this.socialType, this.socialAuthId, this.name);
    }

    @Override
    public User registerUser(LocalDateTime registrationDate) {
        return User.register(this.name, registrationDate);
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
