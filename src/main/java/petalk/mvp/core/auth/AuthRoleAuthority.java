package petalk.mvp.core.auth;

import petalk.mvp.auth.domain.command.UserAuthority;

/**
 * 세션에 저장되어있는 유저 권한을 나타내는 enum 클래스입니다.
 */
public enum AuthRoleAuthority {

    ROLE_PET_OWNER, ROLE_VET;

    public static AuthRoleAuthority from(UserAuthority authority) {
        return switch (authority) {
            case PET_OWNER -> ROLE_PET_OWNER;
            case VET -> ROLE_VET;
            default -> throw new IllegalArgumentException("존재하지 않는 권한입니다.");
        };
    }
}
