package petalk.mvp.http.auth.request;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * 소셜 프로필 요청에 대한 응답을 나타냅니다.
 *
 * 응답에 대한 상태를 확인할 수 있습니다.
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class NaverProfileResponse {

    private HttpStatusCode statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("response")
    private UserInfo user;


    public static NaverProfileResponse from(ResponseEntity<String> response, Gson gson) {
        NaverProfileResponse profileResponse = gson.fromJson(response.getBody(), NaverProfileResponse.class);
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

    private NaverProfile toSocialProfile() {

        return NaverProfile.of(user.id, user.email, user.nickname, user.name, user.gender, user.age, user.birthday, user.profileImage, user.birthYear, user.mobile);
    }

    @NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    static class UserInfo {
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
    }
}
