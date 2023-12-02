package petalk.mvp.auth.domain;

/**
 * 유저 권한을 나타내는 클래스입니다.
 */
public class UserAuthorities {

    private Authority authority;

    private UserAuthorities(Authority authority) {
        this.authority = authority;
    }

    /**
     * 반려인 권한을 생성합니다.
     * @return 반려인 권한
     */
    public static UserAuthorities petOwner() {
        return new UserAuthorities(Authority.PET_OWNER);
    }

    /**
     * 수의사 권한을 생성합니다.
     * @return 수의사 권한
     */
    public static UserAuthorities vet() {
        return new UserAuthorities(Authority.VET);
    }

    /**
     * 계정 권한을 의미합니다.
     */
    public enum Authority {
        // 반려인을 의미합니다.
        PET_OWNER,
        // 수의사를 의미합니다.
        VET
    }
}
