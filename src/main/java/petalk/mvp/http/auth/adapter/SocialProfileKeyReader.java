package petalk.mvp.http.auth.adapter;

import petalk.mvp.domain.auth.AuthorizationCode;
import petalk.mvp.domain.auth.SocialType;

import java.util.Optional;

/**
 * 프로필 획득을 위한 키를 불러오는 인터페이스입니다.
 */
public interface SocialProfileKeyReader {

    Optional<SocialTokenResponse> getKey(AuthorizationCode code);

    boolean isCorrectType(SocialType type);
}
