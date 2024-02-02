package petalk.mvp.auth.domain.command;

/**
 * 써드 파티 유저 인터페이스입니다.
 *
 */
public interface SocialAuthUser {

    SocialAuthId getSocialId();

    String getEmail();

    String getName();

    String getNickname();

    SocialType getSocialType();
}
