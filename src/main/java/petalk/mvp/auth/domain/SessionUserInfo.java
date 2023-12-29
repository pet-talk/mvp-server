package petalk.mvp.auth.domain;

/**
 * 세션을 나타내는 클래스입니다.
 *
 */
public class SessionUserInfo {
    private User.UserId id;
    private String nickname;
    private UserAuthority authorities;

    //== 생성 메소드 ==//
    public SessionUserInfo(User.UserId id, String nickname, UserAuthority authorities) {
        this.id = id;
        this.nickname = nickname;
        this.authorities = authorities;
    }

    public static SessionUserInfo register(User user) {
        return new SessionUserInfo(user.getId(), user.getNickname(), user.getUserAuthority());
    }

    //== 비즈니스 로직 ==//
    //== 수정 메소드 ==//
    //== 조회 메소드 ==//

    public User.UserId getId() {
        return id;
    }
    public String getNickname() {
        return nickname;
    }

    public UserAuthority getAuthority() {
        return authorities;
    }

}
