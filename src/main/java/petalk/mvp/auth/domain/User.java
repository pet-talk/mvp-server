package petalk.mvp.auth.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 인증된 유저를 나타내는 클래스입니다.
 */
public class User {
    private UserId id;
    private String nickname;
    private UserAuthorities authorities;
    private LocalDateTime registrationDate;
    private RegistrationType registrationType;

    private enum RegistrationType {
        EXIST, NEW
    }

    //== 생성 메소드 ==//
    private User(UserId id, String nickname, UserAuthorities authorities, LocalDateTime registrationDate, RegistrationType registrationType) {
        this.id = id;
        this.nickname = nickname;
        this.authorities = authorities;
        this.registrationDate = registrationDate;
        this.registrationType = registrationType;
    }

    /**
     * 기존 반려인 유저를 생성합니다.
     * @param id 반려인 유저의 id
     * @return 반려인 유저
     */
    public static User exist(User.UserId id, String nickname, UserAuthority authority, LocalDateTime registrationDate) {
        return new User(id, nickname, UserAuthorities.from(authority), registrationDate, RegistrationType.EXIST);
    }

    /**
     * 새로운 반려인 유저를 생성합니다.
     * @return 반려인 유저
     */
    public static User register(SocialAuthUser user, LocalDateTime registrationDate) {
        return new User(UserId.register(), user.getNickname(), UserAuthorities.petOwner(), registrationDate, RegistrationType.NEW);
    }

    //== 비즈니스 로직 ==//
    public boolean isNew() {
        return registrationType == RegistrationType.NEW;
    }
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
