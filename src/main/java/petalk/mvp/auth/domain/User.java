package petalk.mvp.auth.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * 인증된 유저를 나타내는 클래스입니다.
 */
public class User {
    private UserId id;
    private UserAuthorities userAuthorities;

    //== 생성 메소드 ==//
    private User(UserId id, UserAuthorities userAuthorities) {
        this.id = id;
        this.userAuthorities = userAuthorities;
    }

    /**
     * 기존 반려인 유저를 생성합니다.
     * @param id 반려인 유저의 id
     * @return 반려인 유저
     */
    public static User existPetOwner(UUID id) {
        return new User(UserId.from(id), UserAuthorities.petOwner());
    }

    /**
     * 기존 수의사 유저를 생성합니다.
     * @param id 수의사 유저의 id
     * @return 수의사 유저
     */
    public static User existVet(UUID id) {
        return new User(UserId.from(id), UserAuthorities.vet());
    }

    /**
     * 새로운 반려인 유저를 생성합니다.
     * @return 반려인 유저
     */
    public static User register() {
        return new User(UserId.register(), UserAuthorities.petOwner());
    }

    //== 비즈니스 로직 ==//
    //== 수정 메소드 ==//
    //== 조회 메소드 ==//

    public UserAuthority getUserAuthority() {
        return userAuthorities.getAuthority();
    }

    public UserId getId() {
        return id;
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