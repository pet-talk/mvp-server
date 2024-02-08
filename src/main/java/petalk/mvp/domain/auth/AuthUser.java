package petalk.mvp.domain.auth;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 인증된 유저를 나타내는 클래스입니다.
 */
public class AuthUser {
    private UserId id;
    private UserSocialInfo socialInfo;
    private String nickname;
    private UserAuthorities authorities;
    private LocalDateTime registrationDate;

    //== 생성 메소드 ==//
    private AuthUser(UserId id, UserSocialInfo socialInfo, String nickname, UserAuthorities authorities, LocalDateTime registrationDate) {
        this.id = id;
        this.socialInfo = socialInfo;
        this.nickname = nickname;
        this.authorities = authorities;
        this.registrationDate = registrationDate;
    }

    /**
     * 기존 반려인 유저를 생성합니다.
     * @param id 반려인 유저의 id
     * @return 반려인 유저
     */
    public static AuthUser exist(AuthUser.UserId id, UserSocialInfo socialInfo, String nickname, UserAuthority authority, LocalDateTime registrationDate) {
        return new AuthUser(id, socialInfo, nickname, UserAuthorities.from(authority), registrationDate);
    }

    public static AuthUser register(String email, SocialType socialType, SocialAuthId socialId, String socialName, String nickname, LocalDateTime registrationDate) {
        UserSocialInfo socialInfo = UserSocialInfo.register(email, socialType, socialId, socialName);
        return new AuthUser(UserId.register(), socialInfo, nickname, UserAuthorities.petOwner(), registrationDate);
    }

    //== 비즈니스 로직 ==//
    //== 수정 메소드 ==//
    //== 조회 메소드 ==//

    public UserAuthority getUserAuthority() {
        return authorities.getAuthority();
    }

    public UserId getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public String getEmail() {
        return socialInfo.getEmail();
    }

    public SocialType getSocialType() {
        return socialInfo.getSocialType();
    }

    public SocialAuthId getSocialId() {
        return socialInfo.getSocialId();
    }

    public String getSocialName() {
        return socialInfo.getSocialName();
    }

    /**
     * 인증된 유저의 id를 나타내는 클래스입니다.
     */
    public static class UserId {

        private UUID id;

        private UserId(UUID id) {
            this.id = id;
        }

        public static UserId register() {
            UUID uuid = UUID.randomUUID();
            return new UserId(uuid);
        }

        public static UserId from(UUID id) {
            return new UserId(id);
        }

        public UUID getValue() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserId userId = (UserId) o;
            return Objects.equals(id, userId.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
