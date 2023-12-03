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

    public static User existPetOwner(UUID id) {
        return new User(UserId.from(id), UserAuthorities.petOwner());
    }

    public static User existVet(UUID id) {
        return new User(UserId.from(id), UserAuthorities.vet());
    }

    //== 비즈니스 로직 ==//
    //== 수정 메소드 ==//
    //== 조회 메소드 ==//

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
