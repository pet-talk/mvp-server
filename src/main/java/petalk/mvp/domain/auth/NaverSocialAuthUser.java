package petalk.mvp.domain.auth;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 네이버 유저의 정보를 나타내는 클래스입니다.
 *
 */
public class NaverSocialAuthUser implements SocialAuthUser{
    private SocialAuthId socialAuthId;
    private String email;
    private String nickname;
    private String name;
    private SocialType socialType;

    //== 생성 메소드 ==//

    private NaverSocialAuthUser(SocialAuthId socialAuthId, String email, String nickname, String name) {
        this.socialAuthId = socialAuthId;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.socialType = SocialType.NAVER;
    }

    public static NaverSocialAuthUser from(SocialAuthId socialAuthId, String email, String nickname, String name) {
        return new NaverSocialAuthUser(socialAuthId, email, nickname, name);
    }
    //== 비즈니스 로직 ==//
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
    public UserSocialInfo registerInfo(User user) {
        return UserSocialInfo.register(user.getId(), this.email, this.socialType, this.socialAuthId, this.name);
    }

    @Override
    public User registerUser(LocalDateTime registrationDate) {
        return User.register(this.nickname, registrationDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NaverSocialAuthUser that = (NaverSocialAuthUser) o;
        return Objects.equals(socialAuthId, that.socialAuthId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socialAuthId);
    }
}
