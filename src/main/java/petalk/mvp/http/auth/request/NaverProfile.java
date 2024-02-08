package petalk.mvp.http.auth.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import petalk.mvp.domain.auth.NaverSocialAuthUser;
import petalk.mvp.domain.auth.SocialAuthId;
import petalk.mvp.domain.auth.SocialAuthUser;
import petalk.mvp.http.auth.adapter.SocialProfile;

/**
 * Naver 프로필을 나타냅니다.
 */
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverProfile implements SocialProfile {
    private String message;
    private Info info;
    @JsonCreator
    private NaverProfile(
            @JsonProperty("message") String message,
            @JsonProperty("response") Info info) {
        this.message = message;
        this.info = info;

        this.validate();
    }

    @Override
    public SocialAuthUser toSocialAuthUser() {
        return this.info.toSocialAuthUser();
    }

    private void validate() {
        this.info.validate();
    }

    public static class Info {

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

        @JsonCreator
        public Info(
                @JsonProperty("id") String id,
                @JsonProperty("email") String email,
                @JsonProperty("nickname") String nickname,
                @JsonProperty("name") String name,
                @JsonProperty("gender") String gender,
                @JsonProperty("age") String age,
                @JsonProperty("birthday") String birthday,
                @JsonProperty("profile_image") String profileImage,
                @JsonProperty("birthyear") String birthYear,
                @JsonProperty("mobile") String mobile) {
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
}
