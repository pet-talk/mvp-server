package petalk.mvp.http.auth.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

//auth domain import
import petalk.mvp.domain.auth.KakaoSocialAuthUser;
import petalk.mvp.domain.auth.SocialAuthId;
import petalk.mvp.domain.auth.SocialAuthUser;

//auth adapter import
import petalk.mvp.http.auth.adapter.SocialProfile;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class KakaoProfile implements SocialProfile {

    private String id;
    private Account account;

    @JsonCreator
    public KakaoProfile(
            @JsonProperty("id") String id,
            @JsonProperty("kakao_account") Account account) {
        this.id = id;
        this.account = account;

        this.validate();
    }

    private void validate() {
        if (id == null || account == null) {
            throw new IllegalArgumentException("KakaoProfile is invalid");
        }

        account.validate();
    }

    @Override
    public SocialAuthUser toSocialAuthUser() {
        return KakaoSocialAuthUser.of(SocialAuthId.from(id), account.email, account.profile.nickname);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @ToString
    private static class Account {
        private String name;
        private String email;
        private Profile profile;

        @JsonCreator
        public Account(
                @JsonProperty("name") String name,
                @JsonProperty("email") String email,
                @JsonProperty("profile") Profile profile) {
            //TODO 카카오에서 이메일을 제공하지 않는 경우가 있음. 이 경우에는 null이 들어옴. 이 경우에 대한 처리가 필요함.
            this.email = email == null ? "kakao" : email;
            //TODO 카카오에서 이름을 제공하지 않는 경우가 있음. 이 경우에는 null이 들어옴. 이 경우에 대한 처리가 필요함.
            this.name = name == null ? "kakao" : name;
            this.profile = profile;
        }


        private void validate() {
            if (email == null || name == null) {
                throw new IllegalArgumentException("카카오 프로필이 올바르지 않습니다.");
            }

            if (email.isBlank() || name.isBlank()) {
                throw new IllegalArgumentException("카카오 프로필이 올바르지 않습니다.");
            }

            profile.validate();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @ToString
    private static class Profile {
        private String profileImage;
        private String nickname;
        @JsonCreator
        public Profile(
                @JsonProperty("profile_image_url") String profileImage,
                @JsonProperty("nickname") String nickname) {

            //TODO 카카오에서 프로필 이미지를 제공하지 않는 경우가 있음. 이 경우에는 null이 들어옴. 이 경우에 대한 처리가 필요함.
            this.profileImage = profileImage == null ? "kakao" : profileImage;

            this.nickname = nickname;
        }


        private void validate() {
            if (profileImage == null || nickname == null) {
                throw new IllegalArgumentException("카카오 프로필이 올바르지 않습니다.");
            }

            if (profileImage.isBlank() || nickname.isBlank()) {
                throw new IllegalArgumentException("카카오 프로필이 올바르지 않습니다.");
            }
        }
    }

}