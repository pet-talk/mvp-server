package petalk.mvp.auth.http.model;

/**
 * third party 서비스의 액세스 토큰 인터페이스입니다.
 */
public interface AccessToken {

    String getTokenHeaderValue();
}