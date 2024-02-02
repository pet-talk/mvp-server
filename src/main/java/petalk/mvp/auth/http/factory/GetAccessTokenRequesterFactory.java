package petalk.mvp.auth.http.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import petalk.mvp.auth.domain.command.Authenticator;
import petalk.mvp.auth.http.request.GetGoogleTokenRequester;
import petalk.mvp.auth.http.request.GetNaverTokenRequester;
import petalk.mvp.auth.http.request.GetSocialTokenRequester;


/**
 * 소셜 액세스 토큰 http 요청자를 생성하는 팩토리 클래스입니다.
 *
 * 제공 받은 Authenticator 를 통해 어떤 소셜 로그인인지 판단하여 해당 소셜 로그인에 맞는 GetSocialTokenRequester 를 반환합니다.
 */
@Component
@RequiredArgsConstructor
public class GetAccessTokenRequesterFactory {

    private final GetNaverTokenRequester naverTokenRequester;
    private final GetGoogleTokenRequester googleTokenRequester;

    public GetSocialTokenRequester getOauthTokenRequester(Authenticator authenticator) {
        if (authenticator.isNaver()) {
            return naverTokenRequester;
        }

        if (authenticator.isGoogle()) {
            return googleTokenRequester;
        }
        return null;
    }
}
