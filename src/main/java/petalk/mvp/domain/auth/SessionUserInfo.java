package petalk.mvp.domain.auth;

/**
 * 세션의 유저 정보를 담는 클래스입니다.
 *
 */
public class SessionUserInfo {
    private User.UserId id;
    private UserAuthority authorities;

    //== 생성 메소드 ==//
    private SessionUserInfo(User.UserId id, UserAuthority authorities) {
        this.id = id;
        this.authorities = authorities;
    }

    public static SessionUserInfo register(User.UserId userId, UserAuthority userAuthority) {
        return new SessionUserInfo(userId, userAuthority);
    }

    //== 비즈니스 로직 ==//
    //== 수정 메소드 ==//
    //== 조회 메소드 ==//

    public User.UserId getId() {
        return id;
    }

    public UserAuthority getAuthority() {
        return authorities;
    }

}
