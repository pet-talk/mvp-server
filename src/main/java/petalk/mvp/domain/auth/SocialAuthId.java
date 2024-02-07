package petalk.mvp.domain.auth;

/**
 * 써드 파티 유저의 id를 나타내는 클래스입니다.
 */
public class SocialAuthId {

    private String id;

    //== 생성 메소드 ==//
    private SocialAuthId(String id) {
        this.id = id;
    }

    public static SocialAuthId from(String id) {
        return new SocialAuthId(id);
    }
    //== 비즈니스 로직 ==//
    //== 수정 메소드 ==//
    //== 조회 메소드 ==//

    public String getValue() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SocialAuthId) {
            SocialAuthId other = (SocialAuthId) obj;
            return id.equals(other.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
