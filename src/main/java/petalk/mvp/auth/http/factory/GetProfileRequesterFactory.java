package petalk.mvp.auth.http.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import petalk.mvp.auth.domain.Authenticator;
import petalk.mvp.auth.http.request.GetNaverProfileRequester;
import petalk.mvp.auth.http.request.GetSocialProfileRequester;

/**
 * 소셜 프로필 http 요청자를 생성하는 팩토리 클래스입니다.
 *
 * 제공 받은 Authenticator 를 통해 어떤 소셜 로그인인지 판단하여 해당 소셜 로그인에 맞는 GetSocialProfileRequester 를 반환합니다.
 */
@Component
@RequiredArgsConstructor
public class GetProfileRequesterFactory {

    private final GetNaverProfileRequester getNaverProfileRequester;

    public GetSocialProfileRequester getOauthTokenRequester(Authenticator authenticator) {
        if (authenticator.isNaver()) {
            return getNaverProfileRequester;
        }
        return null;
    }
}
