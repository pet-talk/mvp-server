package petalk.mvp.domain.auth;

/**
 * 계정 권한을 의미합니다.
 */
public enum UserAuthority {
    // 반려인을 의미합니다.
    PET_OWNER,
    // 수의사를 의미합니다.
    VET;

    public static UserAuthority from(String userAuthority) {
        return UserAuthority.valueOf(userAuthority);
    }
}
