package petalk.mvp.auth.domain;

import java.util.Objects;

/**
 * 네이버 유저의 정보를 나타내는 클래스입니다.
 *
 */
public class NaverSocialAuthUser implements SocialAuthUser{
    private SocialAuthId socialAuthId;

    //== 생성 메소드 ==//
    private NaverSocialAuthUser(SocialAuthId socialAuthId) {
        this.socialAuthId = socialAuthId;
    }

    public static NaverSocialAuthUser from(SocialAuthId socialAuthId) {
        return new NaverSocialAuthUser(socialAuthId);
    }
    //== 비즈니스 로직 ==//
    //== 수정 메소드 ==//
    //== 조회 메소드 ==//

    @Override
    public SocialAuthId getSocialAuthId() {
        return socialAuthId;
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
