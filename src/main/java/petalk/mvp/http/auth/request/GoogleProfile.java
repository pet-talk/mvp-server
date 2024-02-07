package petalk.mvp.http.auth.request;

import lombok.Builder;
import petalk.mvp.domain.auth.GoogleSocialAuthUser;
import petalk.mvp.domain.auth.SocialAuthId;
import petalk.mvp.domain.auth.SocialAuthUser;

/**
 * Naver 프로필을 나타냅니다.
 */
public class GoogleProfile implements SocialProfile {
    private Profile response;

    @Override
    public SocialAuthUser toSocialAuthUser() {
        SocialAuthId id = SocialAuthId.from(response.id);
        return GoogleSocialAuthUser.from(id, response.email, response.name);
    }

    public GoogleProfile(String id, String email, String name, String profileImage, String givenName, String familyName, String locale, boolean verifiedEmail) {
        Profile profile = Profile.builder()
                .id(id)
                .email(email)
                .name(name)
                .givenName(givenName)
                .familyName(familyName)
                .profileImage(profileImage)
                .locale(locale)
                .emailVerified(verifiedEmail)
                .build();
        profile.validate();
        this.response = profile;
    }

    private static class Profile {
        private String id;
        private String email;
        private boolean emailVerified;
        private String name;
        private String givenName;
        private String familyName;
        private String profileImage;
        private String locale;

        @Builder
        public Profile(String id, String email, boolean emailVerified, String name, String givenName, String familyName, String profileImage, String locale) {
            this.id = id;
            this.email = email;
            this.emailVerified = emailVerified;
            this.name = name;
            this.givenName = givenName;
            this.familyName = familyName;
            this.profileImage = profileImage;
            this.locale = locale;
        }

        private void validate() {
            if (id == null || email == null || name == null) {
                throw new IllegalArgumentException("Google 프로필이 올바르지 않습니다.");
            }

            if (id.isBlank() || email.isBlank() || name.isBlank()) {
                throw new IllegalArgumentException("Google 프로필이 올바르지 않습니다.");
            }
        }
    }
}
