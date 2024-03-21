package petalk.mvp.http.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import petalk.mvp.domain.auth.GoogleSocialAuthUser;
import petalk.mvp.domain.auth.SocialAuthId;
import petalk.mvp.domain.auth.SocialAuthUser;
import petalk.mvp.http.auth.adapter.SocialProfile;

/**
 * Naver 프로필을 나타냅니다.
 */
public class GoogleProfile implements SocialProfile {
    private String id;
    private String email;
    private boolean emailVerified;
    private String name;
    private String givenName;
    private String familyName;
    private String profileImage;
    private String locale;

    public GoogleProfile(
            @JsonProperty("sub") String id,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("picture") String profileImage,
            @JsonProperty("given_name") String givenName,
            @JsonProperty("family_name") String familyName,
            @JsonProperty("locale") String locale,
            @JsonProperty("email_verified") boolean verifiedEmail) {

        this.id = id;
        this.email = email;
        this.emailVerified = verifiedEmail;
        this.givenName = givenName;
        this.familyName = familyName;
        this.profileImage = profileImage;
        this.locale = locale;
        //TODO 구글에서 닉네임은 필수가 아님. 이 경우에는 null이 들어옴. 이 경우에 대한 처리가 필요함.
        this.name = name == null ? familyName + givenName : name;

        this.validate();
    }

    @Override
    public SocialAuthUser toSocialAuthUser() {
        SocialAuthId id = SocialAuthId.from(this.id);
        return GoogleSocialAuthUser.of(id, this.email, this.familyName + this.givenName, this.name);
    }

    private void validate() {
        if (id == null || email == null || name == null || givenName == null || familyName == null) {
            throw new IllegalArgumentException("Google 프로필이 올바르지 않습니다.");
        }

        if (id.isBlank() || email.isBlank() || name.isBlank() || givenName.isBlank() || familyName.isBlank()){
            throw new IllegalArgumentException("Google 프로필이 올바르지 않습니다.");
        }
    }
}
