package petalk.mvp.domain.auth;

import java.util.Objects;

/**
 * 유저의 소셜 정보를 나타내는 클래스입니다.
 */
public class UserSocialInfo {

    private SocialInfoId id;

    private String email;

    private SocialType socialType;

    private SocialAuthId socialId;

    private String socialName;

    //== 생성 메소드 ==//
    private UserSocialInfo(SocialInfoId id, String email, SocialType socialType, SocialAuthId socialId, String socialName) {
        this.id = id;
        this.email = email;
        this.socialType = socialType;
        this.socialId = socialId;
        this.socialName = socialName;
    }

    private UserSocialInfo(String email, SocialType socialType, SocialAuthId socialId, String socialName) {
        this.email = email;
        this.socialType = socialType;
        this.socialId = socialId;
        this.socialName = socialName;
    }

    public static UserSocialInfo exist(SocialInfoId id, String email, SocialType socialType, SocialAuthId socialId, String socialName) {
        return new UserSocialInfo(id, email, socialType, socialId, socialName);
    }

    public static UserSocialInfo register(String email, SocialType socialType, SocialAuthId socialId, String socialName) {
        return new UserSocialInfo(email, socialType, socialId, socialName);
    }

    //== 조회 메소드 ==//
    public SocialInfoId getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public SocialType getSocialType() {
        return socialType;
    }

    public SocialAuthId getSocialId() {
        return socialId;
    }

    public String getSocialName() {
        return socialName;
    }

    public static class SocialInfoId {

        private Long id;

        private SocialInfoId(Long id) {
            this.id = id;
        }

        public static SocialInfoId from(Long id) {
            return new SocialInfoId(id);
        }

        public Long getValue() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SocialInfoId that = (SocialInfoId) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
