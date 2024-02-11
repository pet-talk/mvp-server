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
    private String nickname;
    private String profileImage;
    private String email;

    @JsonCreator
    public KakaoProfile(
            @JsonProperty("sub") String id,
            @JsonProperty("nickname") String nickname,
            @JsonProperty("picture") String profileImage,
            @JsonProperty("email") String email) {
        this.id = id;
        this.nickname = nickname;
        this.profileImage = profileImage;
        //TODO 카카오에서 이메일을 제공하지 않는 경우가 있음. 이 경우에는 null이 들어옴. 이 경우에 대한 처리가 필요함.
        this.email = email == null ? "kakao" : email;
    }

    @Override
    public SocialAuthUser toSocialAuthUser() {
        return KakaoSocialAuthUser.of(SocialAuthId.from(id), email, nickname);
    }
}