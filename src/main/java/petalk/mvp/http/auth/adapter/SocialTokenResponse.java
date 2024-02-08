package petalk.mvp.http.auth.adapter;

/**
 * third party 서비스의 프로필을 얻기 위한 키를 생성하는 인터페이스입니다.
 */
public interface SocialTokenResponse {

    String generateKey();
}