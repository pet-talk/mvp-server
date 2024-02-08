package petalk.mvp.http.auth.request;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import petalk.mvp.http.auth.adapter.SocialProfile;

import java.util.Optional;

/**
 * 소셜 프로필 요청에 대한 응답을 나타냅니다.
 *
 * 응답에 대한 상태를 확인할 수 있습니다.
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class GoogleProfileResponse {

    private HttpStatusCode statusCode;
    private String sub;
    private String email;
    @SerializedName("email_verified")
    private boolean emailVerified;
    private String name;
    @SerializedName("given_name")
    private String givenName;
    @SerializedName("family_name")
    private String familyName;
    @SerializedName("picture")
    private String profileImage;
    private String locale;

    public static GoogleProfileResponse from(ResponseEntity<String> response, Gson gson) {
        GoogleProfileResponse profileResponse = gson.fromJson(response.getBody(), GoogleProfileResponse.class);
        profileResponse.addResponse(response);
        return profileResponse;
    }

    private void addResponse(ResponseEntity<String> response) {
        this.statusCode = response.getStatusCode();
    }

    public boolean isOk() {
        return statusCode.is2xxSuccessful();
    }
    public boolean isFailed() {
        return !isOk();
    }

    public Optional<SocialProfile> mapProfile() {
        if(isOk()) {
            return Optional.of(toSocialProfile());
        }
        return Optional.empty();
    }

    private GoogleProfile toSocialProfile() {

        return new GoogleProfile(sub, email, name, profileImage, givenName, familyName, locale, emailVerified);
    }
}
