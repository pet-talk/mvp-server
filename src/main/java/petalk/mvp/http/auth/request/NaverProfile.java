package petalk.mvp.http.auth.request;

import petalk.mvp.domain.auth.NaverSocialAuthUser;
import petalk.mvp.domain.auth.SocialAuthId;
import petalk.mvp.domain.auth.SocialAuthUser;

/**
 * Naver 프로필을 나타냅니다.
 */
public class NaverProfile implements SocialProfile {
    private String id;
    private String email;
    private String nickname;
    private String name;
    private String gender;
    private String age;
    private String birthday;
    private String profileImage;
    private String birthYear;
    private String mobile;

    private NaverProfile(String id, String email, String nickname, String name, String gender, String age, String birthday, String profileImage, String birthYear, String mobile) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.birthday = birthday;
        this.profileImage = profileImage;
        this.birthYear = birthYear;
        this.mobile = mobile;
    }

    public static NaverProfile of(String id, String email, String nickname, String name, String gender, String age, String birthday, String profileImage, String birthYear, String mobile) {
        NaverProfile naverProfile = new NaverProfile(id, email, nickname, name, gender, age, birthday, profileImage, birthYear, mobile);
        naverProfile.validate();
        return naverProfile;
    }

    @Override
    public SocialAuthUser toSocialAuthUser() {
        SocialAuthId socialAuthId = SocialAuthId.from(id);
        return NaverSocialAuthUser.from(socialAuthId, email, nickname, name);
    }

    private void validate() {
        if (id == null || email == null || nickname == null || name == null) {
            throw new IllegalArgumentException("Naver 프로필이 올바르지 않습니다.");
        }

        if (id.isBlank() || email.isBlank() || nickname.isBlank() || name.isBlank()) {
            throw new IllegalArgumentException("Naver 프로필이 올바르지 않습니다.");
        }
    }
}
