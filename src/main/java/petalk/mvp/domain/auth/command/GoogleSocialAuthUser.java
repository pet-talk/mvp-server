package petalk.mvp.domain.auth.command;

import java.util.Objects;

/**
 * 구글 유저의 정보를 나타내는 클래스입니다.
 *
 */
public class GoogleSocialAuthUser implements SocialAuthUser{
    private SocialAuthId socialAuthId;
    private String email;
    private String name;

    //== 생성 메소드 ==//

    private GoogleSocialAuthUser(SocialAuthId socialAuthId, String email, String name) {
        this.socialAuthId = socialAuthId;
        this.email = email;
        this.name = name;
    }

    public static GoogleSocialAuthUser from(SocialAuthId socialAuthId, String email, String name) {
        return new GoogleSocialAuthUser(socialAuthId, email, name);
    }
    //== 비즈니스 로직 ==//
    //== 수정 메소드 ==//
    //== 조회 메소드 ==//

    @Override
    public SocialAuthId getSocialId() {
        return socialAuthId;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNickname() {
        return name;
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.NAVER;
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
