package petalk.mvp.auth.domain;

/**
 * 보유중인 유저 권한을 나타내는 클래스입니다.
 */
public class UserAuthorities {

    private UserAuthority authority;

    private UserAuthorities(UserAuthority authority) {
        this.authority = authority;
    }

    /**
     * 반려인 권한을 생성합니다.
     * @return 반려인 권한
     */
    public static UserAuthorities petOwner() {
        return new UserAuthorities(UserAuthority.PET_OWNER);
    }

    /**
     * 수의사 권한을 생성합니다.
     * @return 수의사 권한
     */
    public static UserAuthorities vet() {
        return new UserAuthorities(UserAuthority.VET);
    }

    public UserAuthority getAuthority() {
        return authority;
    }

}
