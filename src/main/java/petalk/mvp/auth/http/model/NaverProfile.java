package petalk.mvp.auth.http.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import petalk.mvp.auth.domain.NaverSocialAuthUser;
import petalk.mvp.auth.domain.SocialAuthId;
import petalk.mvp.auth.domain.SocialAuthUser;

/**
 * Naver 프로필을 나타냅니다.
 */
public class NaverProfile implements SocialProfile {
    @SerializedName("resultcode")
    private String resultCode;
    private String message;
    private Profile response;

    @Override
    public SocialAuthUser toSocialAuthUser() {
        SocialAuthId id = SocialAuthId.from(response.id);
        return NaverSocialAuthUser.from(id, response.email, response.nickname, response.name);
    }

    public NaverProfile(String resultCode, String message, String id, String email, String nickname, String name, String gender, String age, String birthday, String profileImage, String birthyear, String mobile) {
        this.resultCode = resultCode;
        this.message = message;
        Profile profile = Profile.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .name(name)
                .gender(gender)
                .age(age)
                .birthday(birthday)
                .profileImage(profileImage)
                .birthYear(birthyear)
                .mobile(mobile)
                .build();
        profile.validate();
        this.response = profile;
    }

    private static class Profile {
        private String id;
        private String email;
        private String nickname;
        private String name;
        private String gender;
        private String age;
        private String birthday;
        @SerializedName("profile_image")
        private String profileImage;
        @SerializedName("birthyear")
        private String birthYear;
        private String mobile;

        @Builder
        private Profile(String id, String email, String nickname, String name, String gender, String age, String birthday, String profileImage, String birthYear, String mobile) {
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

        public void validate() {
            if (id == null || email == null || nickname == null || name == null) {
                throw new IllegalArgumentException("Naver 프로필이 올바르지 않습니다.");
            }

            if (id.isBlank() || email.isBlank() || nickname.isBlank() || name.isBlank()) {
                throw new IllegalArgumentException("Naver 프로필이 올바르지 않습니다.");
            }
        }
    }
}
